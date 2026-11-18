import { createContext, useState, useEffect } from "react";

export const DataContext = createContext();

export const DataProvider = ({ children }) => {
  const [barbero, setBarbero] = useState([]);
  const [clientes, setClientes] = useState([]);
  const [servicios, setServicios] = useState([]);
  const [turno, setTurno] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const [resBarbero, resClientes, resServicios, resTurno] =
          await Promise.all([
            fetch("/api/barberos"),
            fetch("/api/clientes"),
            fetch("/api/servicios"),
            fetch("/api/turnos"),
          ]);
        const leerLista = async (respuesta) => {
          if (!respuesta.ok) {
            console.error(
              `Error al cargar ${respuesta.url}: ${respuesta.status}`,
            );
            return [];
          }

          const datos = await respuesta.json();
          return Array.isArray(datos) ? datos : [];
        };

        setBarbero(await leerLista(resBarbero));
        setClientes(await leerLista(resClientes));
        setServicios(await leerLista(resServicios));
        setTurno(await leerLista(resTurno));
      } catch (err) {
        console.error("Error cargando datos:", err);
      } finally {
        setLoading(false);
      }
    };
    fetchData();
  }, []);

  return (
    <DataContext.Provider
      value={{ barbero, clientes, setServicios, servicios, turno, loading }}
    >
      {children}
    </DataContext.Provider>
  );
};
