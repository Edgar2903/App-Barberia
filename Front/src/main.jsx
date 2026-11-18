import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { Barberia } from './Barberia'
import '../src/index.css'
import { BrowserRouter } from 'react-router-dom'
import { DataProvider } from './context/DataProvider'
import { AuthProvider } from './context/AuthContext'


createRoot(document.getElementById("root")).render(
  <BrowserRouter>
    <StrictMode>
      <AuthProvider>
      <DataProvider>
        <Barberia />
      </DataProvider>
    </AuthProvider>
    </StrictMode>
  </BrowserRouter>
);
