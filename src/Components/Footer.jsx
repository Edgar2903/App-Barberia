import React from "react";
import '../styles/Footer.css'
import logo from '../assets/barber.jpg' 

export const Footer = () => {
  const year = new Date().getFullYear(); // Año actual dinámico

  return (
    <footer className="footer">
      <img src={logo} alt="logo empresa" className="footer-logo" />
      <p>© {year} Barbería Real. Todos los derechos reservados.</p>
    </footer>
  );
};

