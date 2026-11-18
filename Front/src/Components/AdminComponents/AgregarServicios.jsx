import { useContext, useEffect, useState } from "react";
import { DataContext } from "../../context/DataProvider";
export const AgregarServicios = () => {
  const { setServicios } = useContext(DataContext);
  const [enlacesImagenes, setEnlacesImagenes] = useState("");
  const [archivos, setArchivos] = useState([]);
  const [categorias, setCategorias] = useState([]);
  const [caracteristicas, setCaracteristicas] = useState([]);

  const [formData, setFormData] = useState({
    nombre: "",
    precio: "",
    descripcion: "",
    categoriaId: "",
    caracteristicaIds: [],
  });

  useEffect(() => {
    const fetchCategorias = async () => {
      try {
        const [categoriasResponse, caracteristicasResponse] = await Promise.all(
          [fetch("/api/categorias"), fetch("/api/caracteristicas")],
        );
        if (!categoriasResponse.ok) {
          throw new Error("Error al obtener las categorías");
        }
        const categoriasData = await categoriasResponse.json();
        setCategorias(categoriasData);

        if (!caracteristicasResponse.ok) {
          throw new Error("Error al obtener las características");
        }
        const caracteristicasData = await caracteristicasResponse.json();
        setCaracteristicas(caracteristicasData);
      } catch (error) {
        console.error("Error al obtener las categorías:", error);
        console.error("Error al obtener las características:", error);
      }
    };

    fetchCategorias();
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleArchivos = (event) => {
    const seleccionados = Array.from(event.target.files);

    const tiposPermitidos = ["image/jpeg", "image/png"];

    if (seleccionados.length > 5) {
      alert("Podés seleccionar hasta 5 imágenes.");
      event.target.value = "";
      setArchivos([]);
      return;
    }

    const archivoInvalido = seleccionados.some(
      (archivo) =>
        !tiposPermitidos.includes(archivo.type) ||
        archivo.size > 2 * 1024 * 1024,
    );

    if (archivoInvalido) {
      alert("Seleccioná imágenes JPG o PNG de hasta 2 MB cada una.");
      event.target.value = "";
      setArchivos([]);
      return;
    }

    setArchivos(seleccionados);
  };

  const handleSubmit = async () => {
    if (!formData.nombre.trim()) {
      alert("El nombre del producto es obligatorio");
      return;
    }
    if (!formData.categoriaId) {
      alert("Seleccioná una categoría");
      return;
    }

    const precio = Number(formData.precio);

    if ((precio) || precio <= 0) {
      alert("El precio debe ser un número mayor que cero");
      return;
    }

    if (!formData.descripcion.trim()) {
      alert("La descripción del producto es obligatoria");
      return;
    }

    const imagenesPorEnlace = enlacesImagenes
      .split("\n")
      .map((enlace) => enlace.trim())
      .filter(Boolean);

    if (archivos.length + imagenesPorEnlace.length > 5) {
      alert("Podés agregar hasta 5 imágenes en total.");
      return;
    }

    const hayEnlacesInvalidos = imagenesPorEnlace.some((enlace) => {
      try {
        const url = new URL(enlace);

        return (
          !["http:", "https:"].includes(url.protocol) || enlace.length > 255
        );
      } catch {
        return true;
      }
    });

    if (hayEnlacesInvalidos) {
      alert(
        "Cada enlace debe comenzar con http:// o https:// y tener hasta 255 caracteres.",
      );
      return;
    }


    try {
      let imagenesSubidas = [];

      if (archivos.length > 0) {
        const datosImagenes = new FormData();

        archivos.forEach((archivo) => {
          datosImagenes.append("files", archivo);
        });
        const respuestaImagenes = await fetch("/api/imagenes", {
          method: "POST",
          credentials: "include",
          body: datosImagenes,
        });

        const datosRespuesta = await respuestaImagenes.json().catch(() => null);

        if (!respuestaImagenes.ok) {
          const mensaje = datosRespuesta
            ? Object.values(datosRespuesta).flat().join("\n")
            : "No se pudieron subir las imágenes";

          throw new Error(mensaje);
        }

        if (!Array.isArray(datosRespuesta?.imagenes)) {
          throw new Error(
            "El servidor no devolvió correctamente las imágenes.",
          );
        }

        // El backend devuelve /imagenes/nombre.jpg.
        // Agregamos /api para utilizar el proxy de Vite.
        imagenesSubidas = datosRespuesta.imagenes.map((ruta) =>
          ruta.startsWith("/api/") ? ruta : `/api${ruta}`,
        );
      }


      const todasLasImagenes = [...imagenesSubidas, ...imagenesPorEnlace];

      const nuevoServicio = {
        nombre: formData.nombre.trim(),
        precio,
        descripcion: formData.descripcion.trim(),
        imagenes: todasLasImagenes.length
          ? todasLasImagenes
          : ["/images/producto-default.jpg"],
        categoriaId: Number(formData.categoriaId),
        caracteristicaIds: formData.caracteristicaIds,
      };
      const response = await fetch("/api/servicios", {
        method: "POST",
        credentials: "include",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(nuevoServicio),
      });
      const datosServicio = await response.json().catch(() => null);

      if (!response.ok) {
        const mensaje = datosServicio
          ? Object.values(datosServicio).flat().join("\n")
          : "Error al guardar servicio";

        throw new Error(mensaje);
      }

      setServicios((serviciosActuales) => [
        ...serviciosActuales,
        datosServicio,
      ]);

      setFormData({
        nombre: "",
        precio: "",
        descripcion: "",
        categoriaId: "",
        caracteristicaIds: [],
      });

      setEnlacesImagenes("");
      setArchivos([]);

      alert("Servicio guardado correctamente");
    } catch (error) {
      alert(error.message || "No se pudo completar el guardado.");
    }
  };

  return (
    <div>
      <h1 className="title-agregar-trabajo">Agregar Producto</h1>
      <input
        type="text"
        name="nombre"
        value={formData.nombre}
        onChange={handleChange}
        placeholder="Nombre"
        className="form-control mb-3"
      />
      <input
        type="number"
        name="precio"
        value={formData.precio}
        onChange={handleChange}
        placeholder="Precio"
        className="form-control mb-3"
      />
     
      <textarea
        name="descripcion"
        value={formData.descripcion}
        onChange={handleChange}
        placeholder="Descripción"
        className="form-control mb-3"
      />
      <label htmlFor="archivosImagenes">Subir imágenes del producto</label>

      <input
        id="archivosImagenes"
        type="file"
        accept="image/jpeg,image/png"
        multiple
        onChange={handleArchivos}
        className="form-control mb-3"
      />

      <textarea
        id="enlacesImagenes"
        className="form-control mb-3"
        placeholder={
          "https://ejemplo.com/foto1.jpg\nhttps://ejemplo.com/foto2.jpg"
        }
        value={enlacesImagenes}
        onChange={(event) => setEnlacesImagenes(event.target.value)}
      />
      <label htmlFor="categoriaId">Categoría:</label>

      <select
        id="categoriaId"
        name="categoriaId"
        value={formData.categoriaId}
        onChange={handleChange}
        className="form-control mb-3"
        required
      >
        <option value="">Seleccioná una categoría</option>

        {categorias.map((categoria) => (
          <option key={categoria.id} value={categoria.id}>
            {categoria.nombre}
          </option>
        ))}
      </select>
      <h3>Características</h3>

      {caracteristicas.map((caracteristica) => (
        <div key={caracteristica.id}>
          <input
            type="checkbox"
            id={`caracteristica-${caracteristica.id}`}
            checked={formData.caracteristicaIds.includes(caracteristica.id)}
            onChange={() => handleCaracteristicaChange(caracteristica.id)}
            
          />

          <label htmlFor={`caracteristica-${caracteristica.id}`}>
            {caracteristica.nombre}
          </label>
        </div>
      ))}

      <button type="button" onClick={handleSubmit} className="form-control">
        Guardar
      </button>
    </div>
  );
};
