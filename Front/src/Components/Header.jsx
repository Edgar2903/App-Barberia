import { useContext } from "react";
import logo from "../assets/barber.jpg";
import { AuthContext } from "../context/AuthContext";
import "../styles/Header.css";
import { Link } from "react-router-dom";

export const Header = () => {
  const { usuario, cerrarSesion } = useContext(AuthContext);
  const iniciales = usuario
    ? `${usuario.nombre?.charAt(0) || ""}${
        usuario.apellido?.charAt(0) || ""
      }`.toUpperCase()
    : "";
  return (
    <nav className="navbar navbar-expand-lg fixed-top">
      <div className="nav">
        <Link to="/">
          <img src={logo} alt="Logo de la empresa" className="logo" />
        </Link>

        <Link to="/" className="navbar-brand">
          <h1 className="title">Barbería</h1>
        </Link>
      </div>

      <div className="d-flex">
        {usuario ? (
          <div className="usuario-header">
            <span className="avatar-usuario">{iniciales}</span>

            <span className="nombre-usuario">{usuario.nombre}</span>
            {usuario.rol === "ADMIN" && (
              <Link to="/admin" className="btn">
                Administración
              </Link>
            )}
            <button type="button" onClick={cerrarSesion} className="btn">
              Cerrar sesión
            </button>
          </div>
        ) : (
          <>
            <Link to="/login" className="btn">
              Iniciar sesión
            </Link>

            <Link to="/registro" className="btn">
              Registrarse
            </Link>
          </>
        )}
      </div>
    </nav>
  );
};
