import { useState } from "react";

export const RegistroUsuario = () => {
  const [mensajeError, setMensajeError] = useState("");
  const [enviando, setEnviando] = useState(false);

  const [formData, setFormData] = useState({
    nombre: "",
    apellido: "",
    email: "",
    password: "",
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    setMensajeError("");

    if (!formData.nombre.trim()) {
      setMensajeError("El nombre es obligatorio");
      return;
    }
    if (!formData.apellido.trim()) {
      setMensajeError("El apellido es obligatorio");
      return;
    }
    if (!formData.email.trim().toLowerCase().match(/^[^\s@]+@[^\s@]+\.[^\s@]+$/)) {
        setMensajeError("El email no es válido");
        return;
    }
    const passwordLength = formData.password.length;
    if (passwordLength < 8 || passwordLength > 72) {
        setMensajeError("La contraseña debe tener entre 8 y 72 caracteres");
        return;
    }
   
    setEnviando(true);

    try { const res = await fetch("/api/usuarios/registrar", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({
        nombre: formData.nombre.trim(),
        apellido: formData.apellido.trim(),
        email: formData.email.trim().toLowerCase(),
        password: formData.password
      
      })
    });

    const datos = await res.json().catch(() => null);

    if (!res.ok) {
      const mensaje = datos
        ? Object.values(datos).flat().join("\n")
        : "Error al registrar usuario";
      throw new Error(mensaje);
    }

    setFormData({
      nombre: "",
      apellido: "",
      email: "",
      password: ""
    });
    alert("Usuario registrado correctamente");

      
   
    } catch (error) {
      console.error("Error al registrar usuario:", error);
      setMensajeError(error.message || "No se pudo completar el registro.");
    } finally {
      setEnviando(false);
    }

  };

  return (
    <>
      <h2>Registro de Usuario</h2>
      <form onSubmit={handleSubmit}>
        <label htmlFor="nombre">Nombre:</label>
        <input
          type="text"
          id="nombre"
          name="nombre"
          value={formData.nombre}
          onChange={handleChange}
          required
        />
        <label htmlFor="apellido">Apellido:</label>
        <input
          type="text"
          id="apellido"
          name="apellido"
          value={formData.apellido}
          onChange={handleChange}
          required
        />
        <label htmlFor="email">Email:</label>
        <input
          type="email"
          id="email"
          name="email"
          value={formData.email}
          onChange={handleChange}
          required
        />
        <label htmlFor="password">Password:</label>
        <input
          type="password"
          id="password"
          name="password"
          value={formData.password}
          onChange={handleChange}
          required
        />
        {mensajeError && <p className="error">{mensajeError}</p>}
        <button type="submit" disabled={enviando}>
          {enviando ? "Registrando..." : "Registrarse"}
        </button>
      </form>
    </>
  );
};
