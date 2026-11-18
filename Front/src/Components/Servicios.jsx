import { useContext, useEffect, useMemo, useState } from "react";
import { DataContext } from "../context/DataProvider";
import "../styles/Home.css";
import { Link } from "react-router-dom";

export const Servicios = ({ categoriaSeleccionada }) => {
  const { servicios, loading } = useContext(DataContext);
  const serviciosFiltrados = categoriaSeleccionada === null
  ? servicios
  : servicios.filter(
      (servicio) =>
        servicio.categoriaId === categoriaSeleccionada
    );
  const [paginaActual, setPaginaActual] = useState(1);
  const porPagina = 10;
  const serviciosAleatorios = useMemo(() => {
    const unicos = Array.from(
      new Map(serviciosFiltrados.map((servicio) => [servicio.id, servicio])).values(),
    );

    for (let i = unicos.length - 1; i > 0; i--) {
      const j = Math.floor(Math.random() * (i + 1));

      [unicos[i], unicos[j]] = [unicos[j], unicos[i]];
    }

    return unicos;
  }, [serviciosFiltrados]);

    useEffect(() => {
  setPaginaActual(1);
}, [categoriaSeleccionada]);

  if (loading) return <p>Cargando...</p>;
  if (servicios.length === 0) {
    return (
      <section className="servicios">
        <h2 className="servicios-title">Servicios</h2>
        <p>No hay productos disponibles.</p>
      </section>
    );
  }

const totalPaginas = Math.ceil(
  serviciosFiltrados.length / porPagina
);
  const serviciosActuales = serviciosFiltrados.slice(
    (paginaActual - 1) * porPagina,
    paginaActual * porPagina,
  );



  return (
    <div className="servicios">
      <h2 className="servicios-title">Servicios recomendados</h2>
      <div className="servicios-container">
        {serviciosActuales.map((s) => (
          <div key={s.id} className="servicios-card">
            <p>{s.nombre}</p>
            {s.categoriaNombre && (
              <p className="servicio-categoria">{s.categoriaNombre}</p>
            )}
            <img
              src={s.imagenes?.[0] || "/images/producto-default.jpg"}
              alt={s.nombre}
              className="servicios-img"
              onError={(event) => {
                event.currentTarget.onerror = null;
                event.currentTarget.src = "/images/producto-default.jpg";
              }}
            />
            <p>{s.precio}</p>
            <Link to={`/servicios/${s.id}`}>Ver detalle</Link>
          </div>
        ))}
      </div>

      {/* Controles de paginación */}
      <div className="paginacion">
        <button
          onClick={() => setPaginaActual(1)}
          disabled={paginaActual === 1}
        >
          Inicio
        </button>
        <button
          onClick={() => setPaginaActual(paginaActual - 1)}
          disabled={paginaActual === 1}
        >
          Atrás
        </button>
        <span>
          Página {paginaActual} de {totalPaginas}
        </span>
        <button
          onClick={() => setPaginaActual(paginaActual + 1)}
          disabled={paginaActual === totalPaginas}
        >
          Adelante
        </button>
        <button
          onClick={() => setPaginaActual(totalPaginas)}
          disabled={paginaActual === totalPaginas}
        >
          Última
        </button>
      </div>
    </div>
  );
};
