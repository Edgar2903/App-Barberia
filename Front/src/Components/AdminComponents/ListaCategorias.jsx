import { useEffect, useState } from "react";

export const ListaCategorias = () => {
  const [categorias, setCategorias] = useState([]);
  const [cargando, setCargando] = useState(true);
  const [error, setError] = useState("");
  const [formData, setFormData] = useState({
    nombre: "",
    descripcion: "",
  });
  const [guardando, setGuardando] = useState(false);
  const [eliminando, setEliminando] = useState(null);
  const [editandoId, setEditandoId] = useState(null);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleEliminar = async (id) => {
    if (
      !window.confirm("¿Estás seguro de que deseas eliminar esta categoría?")
    ) {
      return;
    }

    setEliminando(id);

    try {
      const response = await fetch(`/api/categorias/${id}`, {
        method: "DELETE",
        credentials: "include",
      });

      if (!response.ok) {
        throw new Error("No se pudo eliminar la categoría");
      }

      setCategorias((categoriasActuales) =>
        categoriasActuales.filter((c) => c.id !== id),
      );
    } catch (error) {
      setError(error.message);
    } finally {
      setEliminando(null);
    }
  };
  const handleEditar = (categoria) => {
    setEditandoId(categoria.id);

    setFormData({
      nombre: categoria.nombre,
      descripcion: categoria.descripcion || "",
    });

    setError("");
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");

    if (!formData.nombre.trim()) {
      setError("El nombre de la categoría es obligatorio");
      return;
    }
    setGuardando(true);

    try {
      const response = await fetch("/api/categorias", {
        method: "POST",
        credentials: "include",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          nombre: formData.nombre.trim(),
          descripcion: formData.descripcion.trim(),
        }),
      });

      const datos = await response.json().catch(() => null);

      if (!response.ok) {
        const mensaje = datos
          ? Object.values(datos).flat().join("\n")
          : "No se pudo agregar la categoría";

        throw new Error(mensaje);
      }

      const nuevaCategoria = datos;
      setCategorias((categoriasActuales) => [
        ...categoriasActuales,
        nuevaCategoria,
      ]);
      setFormData({ nombre: "", descripcion: "" });
    } catch (error) {
      setError(error.message);
    } finally {
      setGuardando(false);
    }
  };

  useEffect(() => {
    const cargarCategorias = async () => {
      try {
        const response = await fetch("/api/categorias");

        if (!response.ok) {
          throw new Error("No se pudieron cargar las categorías");
        }
        const datos = await response.json();
        setCategorias(datos);
      } catch (error) {
        setError(error.message);
      } finally {
        setCargando(false);
      }
    };
    cargarCategorias();
  }, []);

  if (cargando) {
    return <p>Cargando categorías...</p>;
  }

  return (
    <div>
      <h2>Administrar categorías</h2>

      {error && (
        <p className="error" role="alert">
          {error}
        </p>
      )}

      <form onSubmit={handleSubmit}>
        <h3>Agregar categoría</h3>

        <input
          type="text"
          name="nombre"
          placeholder="Nombre de la categoría"
          value={formData.nombre}
          onChange={handleChange}
          required
        />

        <textarea
          name="descripcion"
          placeholder="Descripción de la categoría"
          value={formData.descripcion}
          onChange={handleChange}
        />

        <button type="submit" disabled={guardando}>
          {guardando ? "Guardando..." : "Agregar categoría"}
        </button>
      </form>

      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Descripción</th>
            <th>Acciones</th>
          </tr>
        </thead>

        <tbody>
          {categorias.length === 0 ? (
            <tr>
              <td colSpan="4">No hay categorías registradas.</td>
            </tr>
          ) : (
            categorias.map((categoria) => (
              <tr key={categoria.id}>
                <td>{categoria.id}</td>
                <td>{categoria.nombre}</td>
                <td>{categoria.descripcion}</td>
                <td>
                  <button type="button" onClick={() => handleEditar(categoria)}>
                    Editar
                  </button>

                  <button
                    type="button"
                    onClick={() => handleEliminar(categoria.id)}
                    disabled={eliminando === categoria.id}
                  >
                    Eliminar
                  </button>
                </td>
              </tr>
            ))
          )}
        </tbody>
      </table>
    </div>
  );
};
