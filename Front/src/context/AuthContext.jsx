import React, { createContext, useState } from 'react'
export const AuthContext = createContext();
export const AuthProvider = ({children}) => {
    const [usuario, setUsuario] = useState(() => {
  const usuarioGuardado = localStorage.getItem("usuario");
  

  if (!usuarioGuardado) {
    return null;
  }

  try {
    return JSON.parse(usuarioGuardado);
  } catch {
    localStorage.removeItem("usuario");
    return null;
  }


});

const iniciarSesion = (usuario) => {
  setUsuario(usuario);
  localStorage.setItem("usuario", JSON.stringify(usuario));
}

const cerrarSesion = async () => {
  try {
    const response = await fetch("/api/usuarios/logout", {
      method: "POST",
      credentials: "include"
    });
    if (!response.ok) {
      throw new Error("Error al cerrar sesión");
    }
  } catch (error) {
    console.error("Error al cerrar sesión:", error);
  } finally {
    setUsuario(null);
    localStorage.removeItem("usuario");
  }
}


  return (
    <AuthContext.Provider value={{ usuario, iniciarSesion, cerrarSesion }}>
      {children}
    </AuthContext.Provider>
  );
};
