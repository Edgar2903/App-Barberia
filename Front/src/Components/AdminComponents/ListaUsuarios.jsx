import { useContext, useEffect, useState } from "react";
import { AuthContext } from "../../context/AuthContext";

export const ListaUsuarios = () => {
  const [usuarios, setUsuarios] = useState([]);
  const [cargando, setCargando] = useState(true);
  const [error, setError] = useState("");
  const { usuario: usuarioActual } = useContext(AuthContext);

  useEffect(() => {
    const fetchUsuarios = async () => {
      try {
        const response = await fetch("/api/usuarios");
        if (!response.ok) {
          throw new Error("Error al obtener los usuarios");
        }
        const data = await response.json();
        setUsuarios(data);
      } catch (error) {
        setError(error.message);
      } finally {
        setCargando(false);
      }
    };

    fetchUsuarios();
  }, []);

  const cambiarRol = async (id, usuario) => {
    const nuevoRol = usuario.rol === "ADMIN" ? "USER" : "ADMIN";
    try {
      const response = await fetch(`/api/usuarios/${id}/rol`, {
        method: "PATCH",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ rol: nuevoRol }),
      });
      const usuarioActualizado = await response.json();
      setUsuarios((usuariosActuales) =>
        usuariosActuales.map((usuario) =>
          usuario.id === usuarioActualizado.id ? usuarioActualizado : usuario,
        ),
      );
    } catch (error) {
      console.error("Error al cambiar el rol del usuario:", error);
    }
  };

  return (
    <div>
      <h1 className="title-agregar-trabajo">Lista Usuarios</h1>
      {cargando ? (
        <p>Cargando...</p>
      ) : error ? (
        <p>Error: {error}</p>
      ) : (
        <table>
          <thead>
            <tr>
              <th>Id</th>
              <th>Nombre</th>
              <th>Apellido</th>
              <th>Email</th>
              <th>Rol</th>
              <th>Acciones</th>
            </tr>
          </thead>
          <tbody>
            {usuarios.map((usuario) => (
              <tr key={usuario.id}>
                <td>{usuario.id}</td>
                <td>{usuario.nombre}</td>
                <td>{usuario.apellido}</td>
                <td>{usuario.email}</td>
                <td>{usuario.rol}</td>
                <td>
                  <button
                    className="btn btn-primary"
                    onClick={() => cambiarRol(usuario.id, usuario)}
                    disabled={usuario.id === usuarioActual?.id}
                  >
                    {usuario.id === usuarioActual?.id
                      ? "Admin actual"
                      : usuario.rol === "ADMIN"
                        ? "Quitar Admin"
                        : "Hacer Admin"}
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
};
