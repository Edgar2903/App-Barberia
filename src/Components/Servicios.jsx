import { useContext, useState } from "react";
import { DataContext } from "../context/DataProvider";
import "../styles/Home.css";
import { Link } from "react-router-dom";

export const Servicios = () => {
  const { servicios, loading } = useContext(DataContext);
  const [paginaActual, setPaginaActual] = useState(1);
  const porPagina = 10;

  if (loading) return <p>Cargando...</p>;

  const totalPaginas = Math.ceil(servicios.length / porPagina);
  const serviciosActuales = servicios.slice(
    (paginaActual - 1) * porPagina,
    paginaActual * porPagina
  );

  return (
    <div className="servicios">
      <h2 className="servicios-title">Servicios</h2>
      <div className="servicios-container">
        {serviciosActuales.map(s => (
          <div key={s.id} className="servicios-card">
            <p>{s.nombre}</p>
            <img src={s.imagen} alt={s.nombre} className="servicios-img" />
            <p>{s.precio}</p>
            <Link to={`/servicios/${s.id}`}>Ver detalle</Link>
          </div>
        ))}
      </div>

      {/* Controles de paginación */}
      <div className="paginacion">
        <button onClick={() => setPaginaActual(1)} disabled={paginaActual === 1}>Inicio</button>
        <button onClick={() => setPaginaActual(paginaActual - 1)} disabled={paginaActual === 1}>Atrás</button>
        <span>Página {paginaActual} de {totalPaginas}</span>
        <button onClick={() => setPaginaActual(paginaActual + 1)} disabled={paginaActual === totalPaginas}>Adelante</button>
        <button onClick={() => setPaginaActual(totalPaginas)} disabled={paginaActual === totalPaginas}>Última</button>
      </div>
    </div>
  );
};

