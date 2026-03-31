import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import "../styles/DetalleServicios.css"

export const DetalleServicios = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [servicio, setServicio] = useState({ imagenes: [] });
  const [cargando, setCargando] = useState(true);
  const [mostrarTodas, setMostrarTodas] = useState(false);

  useEffect(() => {
    const fetchServicio = async () => {
      try {
        const res = await fetch(`http://localhost:8080/servicios/${id}`);
        if (!res.ok) throw new Error("Servicio no encontrado");
        const data = await res.json();
        setServicio(data);
      } catch (err) {
        console.error(err);
      } finally {
        setCargando(false);
      }
    };
    fetchServicio();
  }, [id]);

  if (cargando) return <p>Cargando...</p>;
  if (!servicio) return <p>No se encontró el servicio.</p>;

  return (
    <div className="detalle-servicio">
      <header>
        <h1>{servicio.nombre}</h1>
        <button onClick={() => navigate(-1)}>Volver</button>
      </header>

      <p>{servicio.descripcion}</p>
      <p>Precio: ${servicio.precio}</p>

      {/* Galería de imágenes */}
      <div className="galeria-imagenes">
        {Array.isArray(servicio.imagenes) && servicio.imagenes.length > 0 ? (
          (mostrarTodas
            ? servicio.imagenes
            : servicio.imagenes.slice(0, 3)
          ).map((img, index) => (
            <img
              key={index}
              src={img}
              alt={`${servicio.nombre} ${index}`}
              className="galeria-img"
            />
          ))
        ) : (
          <span>Sin imágenes</span>
        )}
      </div>

      {Array.isArray(servicio.imagenes) && servicio.imagenes.length > 3 && (
        <button onClick={() => setMostrarTodas(!mostrarTodas)}>
          {mostrarTodas ? "Ver menos" : "Ver más"}
        </button>
      )}
    </div>
  );
};
