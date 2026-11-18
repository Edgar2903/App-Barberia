import { useEffect, useState } from "react";

export const ListaCaracteristicas = () => {
  const [caracteristicas, setCaracteristicas] = useState([]);
  const [nombre, setNombre] = useState("");
  const [guardando, setGuardando] = useState(false);
  const [eliminando, setEliminando] = useState(null);
  const [editandoId, setEditandoId] = useState(null);
  const [cargando, setCargando] = useState(true);

  useEffect(() => {
    const fetchCaracteristicas = async () => {
      try {
        const response = await fetch("/api/caracteristicas");
        if (!response.ok) {
          throw new Error("Error al obtener las características");
        }
        const data = await response.json();
        setCaracteristicas(data);
      } catch (error) {
        setError(error.message);
      } finally {
        setCargando(false);
      }
    };

    fetchCaracteristicas();
  }, []);

  const handleSubmit = async (event) => {
    setError("");

    if (!nombre.trim()) {
      setError("El nombre es obligatorio");
      return;
    }
    event.preventDefault();
    setGuardando(true);
    const esEdicion = editandoId !== null;

    const url = esEdicion
      ? `/api/caracteristicas/${editandoId}`
      : "/api/caracteristicas";

    const metodo = esEdicion ? "PUT" : "POST";
    try {
      const response = await fetch(url, {
        method: metodo,
        credentials: "include",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ nombre: nombre.trim() }),
      });
      if (!response.ok) {
        throw new Error("Error al agregar la característica");
      }
      const nuevaCaracteristica = await response.json();
      setCaracteristicas((actuales) => [...actuales, nuevaCaracteristica]);
      if (esEdicion) {
        setCaracteristicas((actuales) =>
          actuales.map((caracteristica) =>
            caracteristica.id === nuevaCaracteristica.id
              ? nuevaCaracteristica
              : caracteristica,
          ),
        );
      } else {
        setCaracteristicas((actuales) => [...actuales, nuevaCaracteristica]);
      }
      setNombre("");
      setEditandoId(null);
    } catch (error) {
      setError(error.message);
    } finally {
      setGuardando(false);
    }
  };
  const handleEliminar = async (id) => {
    if (!window.confirm("¿Eliminar esta característica?")) {
      return;
    }

    setError("");
    setEliminando(id);

    try {
      const response = await fetch(`/api/caracteristicas/${id}`, {
        method: "DELETE",
        credentials: "include",
      });

      if (!response.ok) {
        throw new Error("No se pudo eliminar la característica");
      }

      setCaracteristicas((actuales) =>
        actuales.filter((caracteristica) => caracteristica.id !== id),
      );
    } catch (error) {
      setError(error.message);
    } finally {
      setEliminando(null);
    }
  };

  const handleEditar = (caracteristica) => {
    setEditandoId(caracteristica.id);
    setNombre(caracteristica.nombre);
    setError("");
  };

  return (
    <div>
      <h2>Administrar características</h2>
      <form onSubmit={handleSubmit}>
        <h3>Agregar característica</h3>

        <input
          type="text"
          placeholder="Nombre de la característica"
          value={nombre}
          onChange={(event) => setNombre(event.target.value)}
          required
        />

        <button type="submit" disabled={guardando}>
          {guardando ? "Agregando..." : "Agregar"}
        </button>
      </form>

      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Acciones</th>
          </tr>
        </thead>

        <tbody>
          {caracteristicas.length === 0 ? (
            <tr>
              <td colSpan="3">No hay características registradas.</td>
            </tr>
          ) : (
            caracteristicas.map((caracteristica) => (
              <tr key={caracteristica.id}>
                <td>{caracteristica.id}</td>
                <td>{caracteristica.nombre}</td>
                <td>
                  <button
                    type="button"
                    onClick={() => handleEditar(caracteristica)}
                  >
                    <h3>
                      {editandoId
                        ? "Editar característica"
                        : "Agregar característica"}
                    </h3>
                    {guardando
                      ? "Guardando..."
                      : editandoId
                        ? "Guardar cambios"
                        : "Agregar"}
                  </button>
                  <button
                    type="button"
                    onClick={() => handleEliminar(caracteristica.id)}
                    disabled={eliminando === caracteristica.id}
                  >
                    {eliminando === caracteristica.id
                      ? "Eliminando..."
                      : "Eliminar"}
                  </button>{" "}
                </td>
              </tr>
            ))
          )}
        </tbody>
      </table>
    </div>
  );
};
