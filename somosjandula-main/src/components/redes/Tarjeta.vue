<template>
    <div class="card" :class="{ 'borderojo': props.estado === 'SIN_SENAL', 'bordeverde': props.estado === 'CONECTADO', 'bordeamarillo': props.estado === 'FALLO_AUTH' }">
        <h3>{{ nombreRed }}</h3>
        <p class="estado" :class="{ 'textrojo': props.estado === 'SIN_SENAL', 'textverde': props.estado === 'CONECTADO', 'textamarillo': props.estado === 'FALLO_AUTH' }">{{ estado }}</p>
        <p class="fecha"><img src="/img/fecha.png" alt="Calendario">{{ fecha}}</p>
        <p class="fecha"><img src="/img/hora.png" alt="Reloj">{{ hora }}</p>
    </div>
</template>

<script setup>

const props = defineProps({
    nombreRed: String,
    estado: String,
    fecha: String,
    hora: String
});


</script>

<style scoped>

.fecha img{
    width: 20px;
    height: 20px;
    margin-right: 9px;
    display: none;
}
.card {
    box-shadow: 2px 2px 20px rgba(0, 0, 0, 0.603);
    border: 2px solid #ccc;
    padding: 20px;
    background-color: #5c5c5cce;
    border-radius: 20px;
    color: #333;
    border: 5px solid;
    transition: border-color 0.8s ease, background-color 0.8s ease, color 0.8s ease;
}

h3 {
    color: rgb(255, 255, 255);
    margin: 0 0 10px 0;
    font-size: 3em;
    font-weight: bold;
    text-shadow: 2px 2px 4px #000000;
}

.estado{
    background-color: #161616;
    border-radius: 20px;
    padding: 9px 17px;
    font-weight: bolder;
    width: fit-content;
    box-shadow: 1px 1px 5px #0000008e;
    color: white;
    transition: color 0.8s ease, background-color 0.8s ease;
}

.fecha {
    font-size: 1.5em;
    color: #ddd;
}

p {
    margin: 5px 0;
}

.borderojo{
    border-color: #c20202;
    background-color: #810909b2;
}

.borderojo img{
    display: inline-block;
}

.bordeverde{
    border-color: rgb(39, 231, 81);
    background-color:  rgba(7, 194, 54, 0.712);
}

.bordeverde img{
    display: inline-block;
}

.bordeamarillo{
    border-color: #f0c400;
    background-color: #dba800e1;
}

.bordeamarillo img{
    display: inline-block;
}

.textverde{
color:rgb(33, 219, 132);
}

.textrojo{
color: #f01616;
}

.textamarillo{
color: #f0ad00;
}

@media screen and (max-width: 768px){
  
.card{
    padding: 1em;
}

h3{
font-size: 1.8em;
margin: 0;
}

.estado{
    font-size: 0.9em;
    padding: 5px 10px;
}

.fecha{
    font-size: 1em;
}
}
</style>