<template>
    <div class="card" :class="{ 'borderojo': props.estado === 'SIN_SENAL', 'bordeverde': props.estado === 'CONECTADO', 'bordeamarillo': props.estado === 'FALLO_AUTH' }">
        <h3>{{ nombreRed }}</h3>
        <p class="estado" :class="{ 'textrojo': props.estado === 'SIN_SENAL', 'textverde': props.estado === 'CONECTADO', 'textamarillo': props.estado === 'FALLO_AUTH' }">{{ estado }}</p>
        <p class="fecha">{{ fecha}}</p>
        <p class="fecha">{{ hora }}</p>
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
.card {
    box-shadow: 2px 2px 20px rgba(0, 0, 0, 0.603);
    border: 1px solid #ccc;
    padding: 20px;
    border-radius: 20px;
    color: #333;
    border: 5px solid;
    max-height: max-content;
    min-width: max-content;
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
    background-color: #252525;
    border-radius: 20px;
    padding: 10px;
    font-weight: bolder;
    width: fit-content;
    box-shadow: 1px 1px 5px #000000;
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
    background-color: #c202026e;
}

.bordeverde{
    border-color: rgb(39, 231, 125);
    background-color:  rgba(7, 194, 54, 0.445);
}

.bordeamarillo{
    border-color: #f0ec00;
    background-color: #ecf00073;
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
}




}
</style>