import { Link, Route, Routes } from "react-router-dom";
import { Header } from "./Components/Header";
import { Home } from "./pages/Home";
import { Admin } from "./pages/Admin";
import { DetalleServicios } from "./pages/DetalleServicios";
import { Footer } from "./Components/Footer";
import { PanelAdmin } from "./Components/PanelAdmin";
import { AgregarServicios } from "./Components/AdminComponents/AgregarServicios";
import { ListaServicios } from "./Components/AdminComponents/ListaProductos";

export const Barberia = () => {
  return (
    <>
      <Header />{" "}
      <main className="container">
        {" "}
        <Routes>
          {" "}
          <Route path="/" element={<Home />} />
          <Route path="/admin/*" element={<Admin />} />{" "}
          <Route path="/servicios/:id" element={<DetalleServicios />} />{" "}
          <Route path="/admin" element={<PanelAdmin />}>
            <Route path="lista-productos" element={<ListaServicios />} />
            <Route path="agregar-producto" element={<AgregarServicios />} />
            {/* <Route path="reservas" element={<ReservasAdmin />} /> */}
          </Route>
        </Routes>{" "}
      </main>{" "}
      {<Footer />}
    </>
  );
};
