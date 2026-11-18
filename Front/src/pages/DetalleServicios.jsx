import { useEffect, useRef, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import "../styles/DetalleServicios.css";

const IMAGEN_DEFAULT = "/images/producto-default.jpg";

function usarImagenDefault(event) {
  const respaldo = new URL(IMAGEN_DEFAULT, window.location.origin).href;

  if (event.currentTarget.src !== respaldo) {
    event.currentTarget.src = respaldo;
  }
}

export const DetalleServicios = () => {
  const { id } = useParams();
  const navigate = useNavigate();

  const [servicio, setServicio] = useState(null);
  const [cargando, setCargando] = useState(true);
  const [error, setError] = useState("");
  const [imagenActual, setImagenActual] = useState(0);

  const dialogoRef = useRef(null);

  useEffect(() => {
    const controller = new AbortController();

    const fetchServicio = async () => {
      setCargando(true);
      setError("");
      setServicio(null);
      setImagenActual(0);
      dialogoRef.current?.close();

      try {
        const res = await fetch(`/api/servicios/${id}`, {
          signal: controller.signal,
        });

        if (!res.ok) {
          throw new Error(
            res.status === 404
              ? "No se encontró el servicio."
              : "No se pudo cargar el servicio.",
          );
        }

        const data = await res.json();

        if (!controller.signal.aborted) {
          setServicio(data);
        }
      } catch (err) {
        if (!controller.signal.aborted) {
          setError(err.message);
        }
      } finally {
        if (!controller.signal.aborted) {
          setCargando(false);
        }
      }
    };

    fetchServicio();

    return () => controller.abort();
  }, [id]);

  if (cargando) return <p>Cargando...</p>;

  if (error || !servicio) {
    return (
      <div className="detalle-servicio">
        <p role="alert">{error || "No se encontró el servicio."}</p>
        <button onClick={() => navigate("/")}>Volver al inicio</button>
      </div>
    );
  }

  const imagenes =
    Array.isArray(servicio.imagenes) && servicio.imagenes.length > 0
      ? servicio.imagenes
      : [IMAGEN_DEFAULT];

  const abrirGaleria = () => {
    setImagenActual(0);
    dialogoRef.current.showModal();
  };

  const cambiarImagen = (paso) => {
    setImagenActual(
      (actual) => (actual + paso + imagenes.length) % imagenes.length,
    );
  };

  return (
    <div className="detalle-servicio">
      <header>
        <h1>{servicio.nombre}</h1>
        <button onClick={() => navigate(-1)}>← Volver</button>
      </header>

      <p>{servicio.descripcion}</p>
      <p>Precio: ${servicio.precio}</p>
      {servicio.caracteristicas?.length > 0 && (
        <section className="detalle-caracteristicas">
          <h3>Características</h3>

          <ul>
            {servicio.caracteristicas.map((caracteristica) => (
              <li key={caracteristica.id}>{caracteristica.nombre}</li>
            ))}
          </ul>
        </section>
      )}

      <div className="galeria-imagenes">
        {imagenes.slice(0, 5).map((img, index) => (
          <img
            key={index}
            src={img || IMAGEN_DEFAULT}
            alt={`${servicio.nombre} - imagen ${index + 1}`}
            className="galeria-img"
            onError={usarImagenDefault}
          />
        ))}
      </div>

      <button type="button" className="boton-ver-mas" onClick={abrirGaleria}>
        Ver más
      </button>

      <dialog
        ref={dialogoRef}
        className="visor-galeria"
        aria-labelledby="titulo-galeria"
      >
        <div className="visor-encabezado">
          <h2 id="titulo-galeria">Fotos de {servicio.nombre}</h2>

          <button type="button" onClick={() => dialogoRef.current.close()}>
            Cerrar
          </button>
        </div>

        <img
          src={imagenes[imagenActual] || IMAGEN_DEFAULT}
          alt={`${servicio.nombre} - imagen ${imagenActual + 1}`}
          className="visor-imagen"
          onError={usarImagenDefault}
        />

        <div className="visor-controles">
          <button
            type="button"
            onClick={() => cambiarImagen(-1)}
            disabled={imagenes.length <= 1}
          >
            Anterior
          </button>

          <span aria-live="polite">
            {imagenActual + 1} de {imagenes.length}
          </span>
          {servicio.categoriaNombre && (
            <p>Categoría: {servicio.categoriaNombre}</p>
          )}
          <button
            type="button"
            onClick={() => cambiarImagen(1)}
            disabled={imagenes.length <= 1}
          >
            Siguiente
          </button>
        </div>
      </dialog>
    </div>
  );
};
