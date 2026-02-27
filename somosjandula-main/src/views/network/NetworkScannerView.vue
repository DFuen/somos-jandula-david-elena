<template>

  <div class="contenedor">
    <Tarjeta v-for="item in listaDeDatos" :key="item.nombreRed" :nombreRed="item.nombreRed" :estado="item.estado"
      :fecha="item.fecha" :hora="item.hora" />
  </div>

  <button ref="administrarButtonRef" class="administrar" type="button" @click="mostrarMenu"><img class="icono-administrar" src="/img/engranaje.png"> Administrar</button>

  <div ref="administracionRef" class="administracion" v-show="mostrarAdministracion">

    <div>
      <h4>Lista de redes disponibles</h4>
      <ul>
        <li v-for="item in listaRedes" :key="item.ssid">
          <span>{{ item.ssid }} </span><button class="borrarred" :id="item.ssid"
            @click="solicitarBorrado(item.ssid)">Borrar</button>
        </li>
      </ul>
    </div>

    <div>
      <form @submit.prevent="enviarNuevaRed">

        <h4>Agregar nueva red</h4>

        <label for="ssid">Nombre de la Red: <span style="color: red;">*</span></label> 
        <input type="text" id="ssid" name="ssid" placeholder="Ejemplo: Andared_Corporativo"
          v-model.trim="nuevaRed.ssid" required>

        <label for="password">Contraseña <span style="color: red;">*</span> </label>
        <input type="password" id="password" name="password" placeholder="******" v-model="nuevaRed.password" required>

        <label for="usuario">Usuario:</label>
        <input type="text" id="usuario" name="usuario" placeholder="Usuario de la red" v-model.trim="nuevaRed.usuario">

        <label for="configuracion">Configuración de red: <span style="color: red;">*</span></label>
        <textarea class="configuracionred" id="configuracion" name="configuracion" v-model.trim="nuevaRed.seguridad" required></textarea>

        <button id="newred" type="submit">Agregar Red</button>

      </form>
    </div>

    <div class="divtiempo">

      <form id="establecerTiempo" @submit.prevent="enviarTiempoConsulta">
              <h4>Establer tiempo de consulta de redes</h4>
        <label for="tiempoConsulta">Tiempo de consulta:</label>
        <div>
          <input type="number" id="tiempoConsulta" name="tiempoConsulta" min="1" v-model.number="tiempoConsulta" required> <span>segundos</span>
        </div>
        <button id="establecer" type="submit">Establecer Tiempo</button>
      </form>

    </div>

  </div>

  <div v-if="mostrarConfirmacionBorrado" class="modal-overlay">
    <div class="modal-confirmacion">
      <p>¿Estás seguro?</p>
      <div class="acciones-modal">
        <button class="btn-cancelar" type="button" @click="cancelarBorrado">Cancelar</button>
        <button class="btn-confirmar" type="button" @click="confirmarBorrado">Borrar</button>
      </div>
    </div>
  </div>


</template>

<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue'
import Tarjeta from '../../components/redes/Tarjeta.vue'
import { obtenerTokenJWTValido } from '@/services/firebaseService'
import { crearToast } from '@/utils/toast.js'

const temporizadorMs = ref(11000);
const tiempoConsulta = ref(11);
const intervalId = ref(null);
const administracionRef = ref(null);
const administrarButtonRef = ref(null);
const apiUrl = 'http://localhost:8084';
const listaDeDatos = ref([]);
const listaRedes = ref([]);
const mostrarAdministracion = ref(false);
const toastMessage = ref('');
const toastColor = ref('success');
const isToastOpen = ref(false);
const nuevaRed = ref({
  ssid: '',
  password: '',
  seguridad: ''
});
const mostrarConfirmacionBorrado = ref(false);
const redPendienteDeBorrado = ref('');

function mostrarMenu() {
  mostrarAdministracion.value = !mostrarAdministracion.value;
    listarRedes();
}

const handleClickOutside = (event) => {
  if (!mostrarAdministracion.value) return;
  const target = event.target;
  const adminEl = administracionRef.value;
  const buttonEl = administrarButtonRef.value;

  if (adminEl && adminEl.contains(target)) return;
  if (buttonEl && buttonEl.contains(target)) return;

  mostrarAdministracion.value = false;
};

//--------------Llamada a la API para pedir las redes(Ahora mismo no hay server asi que lo comento)----------------------
async function datosRedes() {
  try {
    const token = await obtenerTokenJWTValido(toastMessage, toastColor, isToastOpen);
    const response = await fetch(`${apiUrl}/registros-redes`, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    });
    if (!response.ok) {
      throw new Error(`Error ${response.status}: ${response.statusText}`);
    }

    const myData = await response.json();

    listaDeDatos.value = myData;

  } catch (error) {
    console.error("Hubo un error al obtener los datos:", error);
  }
}

const startInterval = () => {
  if (intervalId.value !== null) {
    window.clearInterval(intervalId.value);
  }
  intervalId.value = window.setInterval(datosRedes, temporizadorMs.value);
};

onMounted(() => {
  listarRedes();
  datosRedes();
  startInterval();
  document.addEventListener('click', handleClickOutside);
});

onBeforeUnmount(() => {
  if (intervalId.value !== null) {
    window.clearInterval(intervalId.value);
    intervalId.value = null;
  }
  document.removeEventListener('click', handleClickOutside);
});


//-----------------Llamada a la API para ver la lista de redes----------------------
async function listarRedes() {
  try {
    const token = await obtenerTokenJWTValido(toastMessage, toastColor, isToastOpen);
    const response = await fetch(`${apiUrl}/configuracion-redes`, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    });
    if (!response.ok) {
      throw new Error(`Error ${response.status}: ${response.statusText}`);
    }

    const myData = await response.json();

    listaRedes.value = myData;

  } catch (error) {
    console.error("Hubo un error al obtener los datos:", error);
  }
}

function solicitarBorrado(ssid) {
  redPendienteDeBorrado.value = ssid;
  mostrarConfirmacionBorrado.value = true;
}

function cancelarBorrado() {
  redPendienteDeBorrado.value = '';
  mostrarConfirmacionBorrado.value = false;
}

async function confirmarBorrado() {
  const ssid = redPendienteDeBorrado.value;
  mostrarConfirmacionBorrado.value = false;
  redPendienteDeBorrado.value = '';

  if (ssid) {
    await borrarRed(ssid);
  }
}

//-----------------Llamada a la API para borrar una red----------------------
async function borrarRed(ssid) {
  try {
    const token = await obtenerTokenJWTValido(toastMessage, toastColor, isToastOpen);

    const response = await fetch(`${apiUrl}/configuracion-redes/${encodeURIComponent(ssid)}`, {
      method: 'DELETE',
      headers: {
        Authorization: `Bearer ${token}`
      }
    });

    if (!response.ok) {
      throw new Error(`Error ${response.status}: ${response.statusText}`);
    }

    await listarRedes();
    crearToast(toastMessage, toastColor, isToastOpen, 'success', 'Red eliminada correctamente');
  } catch (error) {
    console.error('Hubo un error al borrar la red:', error);
    crearToast(toastMessage, toastColor, isToastOpen, 'danger', error.message || 'Error al borrar la red');
  }
}

//-----------------Guardar un nueva red----------------------
async function enviarNuevaRed() {
  try {
    const token = await obtenerTokenJWTValido(toastMessage, toastColor, isToastOpen);

    const response = await fetch(`${apiUrl}/configuracion-redes`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`
      },
      body: JSON.stringify(nuevaRed.value)
    });

    if (!response.ok) {
      throw new Error(`Error ${response.status}: ${response.statusText}`);
    }

    nuevaRed.value = { ssid: '', password: '', usuario:'', seguridad: '' };
    
    await listarRedes();
    await datosRedes();
    crearToast(toastMessage, toastColor, isToastOpen, 'success', 'Red agregada correctamente');
  } catch (error) {
    console.error('Hubo un error al agregar la red:', error);
    crearToast(toastMessage, toastColor, isToastOpen, 'danger', error.message || 'Error al agregar la red');
  }
}

//-----------------Establecer tiempo de consulta----------------------
function enviarTiempoConsulta() {
  const nuevoTiempo = tiempoConsulta.value;

  if (!Number.isFinite(nuevoTiempo) || nuevoTiempo < 1) {
    crearToast(toastMessage, toastColor, isToastOpen, 'danger', 'Por favor, ingresa un número válido mayor a 0');
    return;
  }

  temporizadorMs.value = nuevoTiempo * 1000;
  startInterval();
  crearToast(toastMessage, toastColor, isToastOpen, 'success', `Tiempo de consulta establecido a ${nuevoTiempo} segundos`);
  console.log(`Nuevo tiempo de consulta: ${temporizadorMs.value} ms`);
}

</script>

<style scoped>
* {
  padding: 0;
  margin: 0;
}

.contenedor {
  margin: 0;
  padding: 50px;
  width: 100%;
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 30px;
  transition: background-color 0.3s ease;
}

li {
  background-color: rgba(0, 0, 0, 0.89);
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px;
  border-radius: 10px;
}

ul {
  display: flex;
  flex-direction: column;
  gap: 10px;
  list-style-type: none;
  padding: 0;

}

#newred {
  background-color: #28a745;
  color: white;
  font-size: 1.2em;
  border: none;
  border-radius: 5px;
  padding: 10px 10px;
  cursor: pointer;
  margin-top: 1.2em;
}

#newred:hover {
  background-color: #1e7e34;
}

.borrarred {
  background-color: #FF4136;
  color: white;
  border: none;
  border-radius: 5px;
  padding: 5px 10px;
  cursor: pointer;
  margin-left: 20px;
}

h4 {
  margin-bottom: 30px;
  color: #61adff;
  font-weight: bold;
  font-size:x-large;
}

input {
  padding: 5px;
  border-radius: 5px;
  border: 1px solid #ccc;
}



.borrarred:hover {
  background-color: #a00a0a;
}

#tiempoConsulta {
  padding: 5px;
  border-radius: 5px;
  border: 1px solid #ccc;
  width: 100px;
}

.administrar {
  position: fixed;
  bottom: 20px;
  right: 20px;
  padding: 10px 20px;
  background-color: #007BFF;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.administrar:hover {
  background-color: #0056b3;
}

form {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.administracion {
  border-radius: 30px;
  display: flex;
  flex-direction: row;
  align-items: end;
  gap: 30px;
  position: fixed;
  bottom: 80px;
  right: 20px;
  padding: 20px;
}

.administracion div {
  backdrop-filter: blur(12px);
  background-color: #0a0a0abe;
  padding: 20px;
  border-radius: 10px;
  width: 400px
}

.modal-overlay {
  position: fixed;
  inset: 0;
  background-color: rgba(0, 0, 0, 0.45);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal-confirmacion {
  background-color: #1f1f1f;
  padding: 20px;
  border-radius: 12px;
  min-width: 280px;
  text-align: center;
  color: #fff;
}

.acciones-modal {
  margin-top: 15px;
  display: flex;
  justify-content: center;
  gap: 12px;
}

.btn-cancelar,
.btn-confirmar {
  border: none;
  border-radius: 6px;
  padding: 8px 14px;
  cursor: pointer;
}

.btn-cancelar {
  background-color: #6c757d;
  color: white;
}

.btn-confirmar {
  background-color: #FF4136;
  color: white;
}

#establecer{
  background-color: #28a745;
  color: white;
  font-size: 1.2em;
  border: none;
  border-radius: 5px;
  padding: 10px 10px;
  cursor: pointer;
}

#establecer:hover {
  background-color: #1e7e34;
}

.configuracionred {
  height: 5em;
}

.icono-administrar {
  width: 20px;
  height: 20px;
  margin-right: 10px;
}

.divtiempo{
  max-height: 400px;
}

@media screen and (max-width: 768px) {

  .contenedor {
    display: flex;
    flex-direction: column;
    gap: 20px;
    padding: 0;
  }


  .administracion {
    margin: 0;
    left: 0;
    right: 0;
    bottom: 80px;
    flex-direction: column;
    width: 100%;
    font-size: 0.9em;
    gap: 5px;
    max-height: calc(100vh - 140px);
    padding-bottom: 20px;
    overflow-y: auto;
    padding: 12px;
    box-sizing: border-box;
  }

  .administracion div {
    margin: 0;
    width: 100%;
  }

  .divtiempo form h4{
    display: flex;
    margin-bottom: 0;
  }

  h4{
    font-size: large;
  }

}
</style>
