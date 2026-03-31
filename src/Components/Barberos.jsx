import React, { useContext } from 'react'
import { DataContext } from '../context/DataProvider'

export const Barberos = () => {


  const {barbero, loading} = useContext(DataContext)

  if (loading) return <p>Cargando...</p>

  return (
    <div className="barberos">
      <h2 className="barberos-title">Barberos Recomendados</h2>
      <div className="barberos-container">
        {barbero.map((b) => (
          <div key={b.id} className="barberos-card"> 
          <h3>{b.nombre} {b.apellido}</h3>
          <img src={b.imagenes} alt={b.nombre} className="barberos-img" />
          <p>{b.valoracion}</p>
          </div>
        ))}
      </div>
    </div>
  )
}
