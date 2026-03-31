import React, { useEffect, useState } from "react";
import { Link, Outlet } from "react-router-dom";
import "../styles/Admin.css";

export const PanelAdmin = () => {
  const [isMobile, setIsMobile] = useState(window.innerWidth < 768);

  useEffect(() => {
    const handleResize = () => setIsMobile(window.innerWidth < 768);
    window.addEventListener("resize", handleResize);
    return () => window.removeEventListener("resize", handleResize);
  }, []);

  if (isMobile) {
    return (
      <p className="panel-no-disponible">
        ¡Este panel de administración no está disponible en dispositivos móviles!
      </p>
    );
  }



  return (
    <div className="admin-layout">
      <aside className="admin-sidebar">
        <h1>Menú</h1>
        <ul>
          <li>
            <Link to="lista-productos">Lista de productos</Link>
          </li>
          <li>
            <Link to="agregar-producto">Agregar producto</Link>
          </li>
        </ul>
      </aside>

      <main className="admin-content">
        <h2 className="title">Panel de Administración</h2>
        <Outlet />
      </main>
    </div>
  );
};
