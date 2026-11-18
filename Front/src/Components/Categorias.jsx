import React, { useEffect, useState } from "react";

export const Categorias = ({ categoriaSeleccionada, onSeleccionar }) => {
  const [categorias, setCategorias] = useState([]);
  const [cargando, setCargando] = useState(true);
  const [error, setError] = useState("");

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

  return (
    <section className="categorias">
      <h2>Categorías</h2>
      <button type="button" onClick={() => onSeleccionar(null)}>
        Todas las categorías
      </button>

      <div className="categorias-grid">
        {categorias.length > 0 ? (
          categorias.map((categoria) => (
            <div
              key={categoria.id}
              className={`categoria-card ${
                categoriaSeleccionada === categoria.id ? "activa" : ""
              }`}
              onClick={() => onSeleccionar(categoria.id)}
            >
              <h3>{categoria.nombre}</h3>
              <p>{categoria.descripcion}</p>
            </div>
          ))
        ) : (
          <p>No hay categorías disponibles.</p>
        )}
      </div>
    </section>
  );
};
