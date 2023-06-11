<!-- JavaScript code to fetch random dog image -->
const container = document.getElementById('dog-image');
container.innerHTML = '<img src="/gifs/spinner-2.gif" alt="Loading" class="loading-gif">';
fetch('https://random.dog/woof.json').then(response => response.json()).then(data => {

    const imageUrl = data.url;
    const dogImageElement = document.getElementById('dog-image');
    dogImageElement.src = imageUrl;
    container.innerHTML = '';
}).catch(error => {
    console.error('Error fetching dog image:', error);
});