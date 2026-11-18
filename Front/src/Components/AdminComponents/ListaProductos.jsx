import React, { useContext } from "react";
import { DataContext } from "../../context/DataProvider";
import Swal from "sweetalert2";



export const ListaServicios = () => {
  const { servicios, setServicios, loading } = useContext(DataContext);

  const handleEliminar = async (id) => {
    const result = await Swal.fire({
      title: '¿Seguro que deseas eliminar este producto?',
      text: 'Esta acción no se puede deshacer',
      icon: 'error',
      showCancelButton: true,
      confirmButtonColor: '#d43737', // dorado
      cancelButtonColor: '#0a3d62',  // azul oscuro
      confirmButtonText: 'Sí, eliminar',
      cancelButtonText: 'Cancelar'
    });

    if (!result.isConfirmed) return;

    try {
      const res = await fetch(`/api/servicios/${id}`, {
        method: "DELETE",
      });

      if (!res.ok) throw new Error("Error al eliminar producto");

      setServicios(servicios.filter(s => s.id !== id));

      Swal.fire({
        title: 'Eliminado',
        text: 'Producto eliminado correctamente',
        icon: 'success',
        confirmButtonColor: '#2aac30'
      });
    } catch (err) {
      console.error(err);
      Swal.fire({
        title: 'Error',
        text: 'No se pudo eliminar el producto',
        icon: 'error',
        confirmButtonColor: '#d49537'
      });
    }
  };

  if (loading) return <p>Cargando...</p>;


  return (
    <div>
      <h1 className="title-agregar-trabajo">Lista Servicios</h1>
      <table>
        <thead>
          <tr>
            <th>Id</th>
            <th>Nombre</th>
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
          {servicios.map(s => (
            <tr key={s.id}>
              <td>{s.id}</td>
              <td>{s.nombre}</td>
              <td>
                <button onClick={() => handleEliminar(s.id)}>Eliminar</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

  