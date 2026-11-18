import "../styles/Home.css";
import { Buscador } from "../Components/Buscador";
import { Servicios } from "../Components/Servicios";
import { Barberos } from "../Components/Barberos";
import { Categorias } from "../Components/Categorias";
import { useState } from "react";

export const Home = () => {
  const [categoriaSeleccionada, setCategoriaSeleccionada] = useState(null);

  return (
    <div className="home-container">
      <Buscador />
      <Categorias
        categoriaSeleccionada={categoriaSeleccionada}
        onSeleccionar={setCategoriaSeleccionada}
      />

      <Servicios categoriaSeleccionada={categoriaSeleccionada} />
      <Barberos />
    </div>
  );
};
