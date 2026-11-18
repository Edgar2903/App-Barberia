import { useContext, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { AuthContext } from "../context/AuthContext";

export const Login = () => {
    const { iniciarSesion } = useContext(AuthContext);
    const navigate = useNavigate();
  const [formData, setFormData] = useState({
    email: "",
    password: "",
  });

  const [mensajeError, setMensajeError] = useState("");
  const [enviando, setEnviando] = useState(false);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    if (
      !formData.email
        .trim()
        .toLowerCase()
        .match(/^[^\s@]+@[^\s@]+\.[^\s@]+$/)
    ) {
      setMensajeError("El email no es válido");
      return;
    }
    const passwordLength = formData.password.length;
    if (passwordLength < 8 || passwordLength > 72) {
      setMensajeError("La contraseña debe tener entre 8 y 72 caracteres");
      return;
    }
    setEnviando(true);
    setMensajeError("");

    try {
        const res = await fetch("/api/usuarios/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(
                {
                    email: formData.email.trim().toLowerCase(),
                    password: formData.password
                }
            )
        });
        const datos = await res.json().catch(() => null);
        if (!res.ok) {
            setMensajeError(datos?.error || "Error al iniciar sesión");
            return;
        }
        iniciarSesion(datos);
        navigate("/"); 
        
    } catch {
      setMensajeError("Error al enviar el formulario. Inténtalo de nuevo.");
    } finally {
      setEnviando(false);
    }
  };

  return (
    <div>
      <h2>Iniciar sesión</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label htmlFor="email">Email:</label>
          <input
            type="email"
            id="email"
            name="email"
            value={formData.email}
            onChange={handleChange}
            required
          />
        </div>
        <div>
          <label htmlFor="password">Password:</label>
          <input
            type="password"
            id="password"
            name="password"
            value={formData.password}
            onChange={handleChange}
            required
          />
        </div>
        <button type="submit" disabled={enviando}>
          {enviando ? "Ingresando..." : "Iniciar sesión"}
        </button>
        <p>
          ¿No tenés una cuenta? <Link to="/registro">Registrate aquí</Link>
        </p>
        {mensajeError && (
          <p className="error" role="alert">
            {mensajeError}
          </p>
        )}
      </form>
    </div>
  );
};
