import { useContext } from "react";
import { Navigate, Outlet } from "react-router-dom";
import { AuthContext } from "../context/AuthContext";

export const RutaAdmin = () => {
  const { usuario } = useContext(AuthContext);

  if (!usuario) {
    return <Navigate to="/login" replace />;
  }

  if (usuario.rol !== "ADMIN") {
    return <Navigate to="/" replace />;
  }

  return <Outlet />;
};