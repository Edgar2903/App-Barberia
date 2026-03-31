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
        const [resBarbero, resClientes, resServicios, resTurno] = await Promise.all([
          fetch("http://localhost:8080/barbero"),
          fetch("http://localhost:8080/cliente"),
          fetch("http://localhost:8080/servicios"),
          fetch("http://localhost:8080/turno")
        ]);

        setBarbero(await resBarbero.json());
        setClientes(await resClientes.json());
        setServicios(await resServicios.json());
        setTurno(await resTurno.json());
      } catch (err) {
        console.error("Error cargando datos:", err);
      } finally {
        setLoading(false);
      }
    };
    fetchData();
  }, []);

  return (
    <DataContext.Provider value={{ barbero, clientes, setServicios, servicios, turno, loading }}>
      {children}
    </DataContext.Provider>
  );
};

