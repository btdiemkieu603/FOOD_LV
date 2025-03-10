import React from 'react';
import ReactDOM from 'react-dom/client';

import App from './App';

import StoreContextProvider from "../src/components/context/StoreContext.jsx";

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <StoreContextProvider>
      <App />
    </StoreContextProvider>
  </React.StrictMode>
);

