import { Navigate, Route, Routes } from "react-router-dom";
import { Header } from "./Components/Header";
import { Home } from "./pages/Home";
import { Admin } from "./pages/Admin";
import { DetalleServicios } from "./pages/DetalleServicios";
import { Footer } from "./Components/Footer";
import { AgregarServicios } from "./Components/AdminComponents/AgregarServicios";
import { ListaServicios } from "./Components/AdminComponents/ListaProductos";
import { RegistroUsuario } from "./pages/RegistroUsuario";
import { Login } from "./pages/Login";
import { RutaAdmin } from "./Components/RutaAdmin";
import { ListaUsuarios } from "./Components/AdminComponents/ListaUsuarios";
import { ListaCategorias } from "./Components/AdminComponents/ListaCategorias";
import { ListaCaracteristicas } from "./Components/AdminComponents/ListaCaracteristicas";

export const Barberia = () => {
  return (
    <>
      <Header />

      <main className="container">
        <Routes>
          <Route path="/" element={<Home />} />

          <Route path="/servicios/:id" element={<DetalleServicios />} />

          <Route path="/registro" element={<RegistroUsuario />} />

          <Route path="/login" element={<Login />} />

          <Route element={<RutaAdmin />}>
            <Route path="/admin" element={<Admin />}>
              <Route path="usuarios" element={<ListaUsuarios />} />
              <Route path="categorias" element={<ListaCategorias />} />

              <Route path="lista-productos" element={<ListaServicios />} />
              <Route
                path="caracteristicas"
                element={<ListaCaracteristicas />} />

              <Route path="agregar-producto" element={<AgregarServicios />} />
            </Route>
          </Route>

          <Route
            path="/administración"
            element={<Navigate to="/admin" replace />}
          />

          <Route
            path="/administracion"
            element={<Navigate to="/admin" replace />}
          />
        </Routes>
      </main>

      <Footer />
    </>
  );
};
