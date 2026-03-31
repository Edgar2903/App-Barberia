import React from "react";
import { useState } from "react";
export const AgregarServicios = () => {
  const [formData, setFormData] = useState({
    nombre: "",
    precio: "",
    descripcion: "",
    imagenUrl: "" // acá guardamos la URL de la imagen
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };
const handleSubmit = async () => {
  const nuevoServicio = {
    nombre: formData.nombre,
    precio: formData.precio,
    descripcion: formData.descripcion,
    imagenUrl: formData.imagenUrl
  };

  try {
    const response = await fetch("http://localhost:8080/servicios", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(nuevoServicio)
    });

if (response.ok) {
  alert("Servicio guardado correctamente");
} else {
  const errorData = await response.json();
  alert(errorData.error || "Error al guardar servicio");
}


  } catch (error) {
    alert("Error de red");
  }
};





  return (
    <div>
      <h1 className="title-agregar-trabajo">Agregar Producto</h1>
      <input
        type="text"
        name="nombre"
        value={formData.nombre}
        onChange={handleChange}
        placeholder="Nombre"
        className="form-control mb-3"
      />
      <input
        type="number"
        name="precio"
        value={formData.precio}
        onChange={handleChange}
        placeholder="Precio"
        className="form-control mb-3"
      />
      <textarea
        name="descripcion"
        value={formData.descripcion}
        onChange={handleChange}
        placeholder="Descripción"
        className="form-control mb-3"
      />
      <input className="form-control mb-3" type="file" multiple onChange={handleChange} />
      <button onClick={handleSubmit} className="form-control ">Guardar</button>
    </div>
  );
};
