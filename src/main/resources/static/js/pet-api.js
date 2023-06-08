function petsByLocation(postalCode, petType, genderType, ageType) {
    // Convert to Token
    fetch(`https://api.petfinder.com/v2/oauth2/token`, {
        method: `POST`,
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: `grant_type=client_credentials&client_id=hp67Thxs2NKYZJ7ZKxS2Azz2ZyAQTodIMNFajy7Qx30iIQ2xZ7&client_secret=XzC8A2nHtHm20r4Up4G5G4dCOZUp7ZBDN6eQDmnc`
    })
        .then(response => response.json())
        .then(token => {
            // Handle the API response here
            let apiUrl = `https://api.petfinder.com/v2/animals?&location=${postalCode}`;

            if (genderType && petType && ageType) {
                apiUrl += `&gender=${genderType}&type=${petType}&age=${ageType}`;
            } else if (genderType && petType) {
                apiUrl += `&gender=${genderType}&type=${petType}`;
            } else if (genderType && ageType) {
                apiUrl += `&gender=${genderType}&age=${ageType}`;
            } else if (petType && ageType) {
                apiUrl += `&type=${petType}&age=${ageType}`;
            } else if (genderType) {
                apiUrl += `&gender=${genderType}`;
            } else if (petType) {
                apiUrl += `&type=${petType}`;
            } else if (ageType) {
                apiUrl += `&age=${ageType}`;
            }

            fetch(apiUrl, {
                method: `GET`,
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token.access_token}`
                }
            })
                .then(response => response.json())
                .then(data => {
                    petCards(data);
                    console.log(data);
                })
                .catch(error => {
                    // Handle any errors that occurred during the request
                    console.error(error);
                });
        })
        .catch(error => {
            // Handle any errors that occurred during the request
            console.error(error);
        });
}


// Get user input from a search form
const petSearch = document.getElementById('petSearch');
const searchInput = document.getElementById('search-bar');
const animalType = document.getElementById('type');
const genderType = document.getElementById('gender-type');
const ageType = document.getElementById('age-type');
animalType.addEventListener('click', e => {
    e.preventDefault();

    let animalPicked = e.target;
    while (animalPicked && !animalPicked.classList.contains('dropdown-item')) {
        animalPicked = animalPicked.parentElement;
    }

    if (animalPicked) {
        // const animalType = animalPicked.dataset.type;

        animalType.querySelectorAll('.dropdown-item').forEach(option => {
            option.classList.remove('active');
        });

        animalPicked.classList.add('active');

        console.log(animalPicked.dataset.type);
    }
});

genderType.addEventListener('click', e => {
    e.preventDefault();

    let genderPicked = e.target;
    while (genderPicked && !genderPicked.classList.contains('dropdown-item')) {
        genderPicked = genderPicked.parentElement;
    }

    if (genderPicked) {
        genderType.querySelectorAll('.dropdown-item').forEach(option => {
            option.classList.remove('active');
        });

        genderPicked.classList.add('active');

        console.log(genderPicked.dataset.gender);
    }
});

ageType.addEventListener('click', e => {
    e.preventDefault();

    let agePicked = e.target;
    while (agePicked && !agePicked.classList.contains('dropdown-item')) {
        agePicked = agePicked.parentElement;
    }

    if (agePicked) {
        ageType.querySelectorAll('.dropdown-item').forEach(option => {
            option.classList.remove('active');
        });

        agePicked.classList.add('active');

        console.log(agePicked.dataset.age);
    }
});



petSearch.addEventListener('submit', e => {
    e.preventDefault();

    const postalCode = searchInput.value;
    let petType = null;
    let genderType = null;
    let ageType = null;

    const petTypeElement = document.querySelector('#type .dropdown-item.active');
    if (petTypeElement) {
        petType = petTypeElement.getAttribute('data-type');
    }

    const genderTypeElement = document.querySelector('#gender-type .dropdown-item.active');
    if (genderTypeElement) {
        genderType = genderTypeElement.getAttribute('data-gender');
    }

    const ageTypeElement = document.querySelector('#age-type .dropdown-item.active');
    if (ageTypeElement) {
        ageType = ageTypeElement.getAttribute('data-age');
    }



    petsByLocation(postalCode, petType, genderType, ageType);
});


//displaying animals
function petCards(data) {
    const container = document.getElementById('animalContainer');
    container.innerHTML = '';


    if (!data || typeof data !== 'object' || Object.keys(data).length === 0) {
        const noResults = document.createElement('p');
        noResults.textContent = 'No animals found.';
        container.appendChild(noResults);
        return;
    }

    // Extract the array of animals from the JSON data
    const animals = data.animals;

    if (!Array.isArray(animals) || animals.length === 0) {
        const noResults = document.createElement('p');
        noResults.textContent = 'No animals found.';
        container.appendChild(noResults);
        return;
    }

    animals.forEach(animal => {
        const card = document.createElement('div');
        card.classList.add('card', 'mx-auto', 'my-3');
        card.style.width = '20rem';

        const image = document.createElement('img');
        image.classList.add('card-img-top', 'mt-3');
        image.style.width = '295px';
        image.style.height = '295px';
        image.src = animal.photos.length > 0 ? animal.photos[0].large : '/img/img_not_found_wide.jpg';
        image.alt = 'Animal Image';

        const cardBody = document.createElement('div');
        cardBody.classList.add('card-body');

        const title = document.createElement('h5');
        title.classList.add('card-title');
        title.textContent = animal.name;

        const description = document.createElement('p');
        description.classList.add('card-text');

        const ul = document.createElement('ul');
        ul.classList.add('list-group', 'list-group-flush');

        const id = document.createElement('li');
        id.classList.add('list-group-item');
        id.textContent = `Id: ${animal.id}`;

        const species = document.createElement('li');
        species.classList.add('list-group-item');
        species.textContent = `Species: ${animal.species}`;

        const breed = document.createElement('li');
        breed.classList.add('list-group-item');
        breed.textContent = `Breed: ${animal.breeds.primary}`;

        const gender = document.createElement('li');
        gender.classList.add('list-group-item');
        gender.textContent = `Gender: ${animal.gender}`;

        const age = document.createElement('li');
        age.classList.add('list-group-item');
        age.textContent = `Age: ${animal.age}`;

        const size = document.createElement('li');
        size.classList.add('list-group-item');
        size.textContent = `Size: ${animal.size}`;

        ul.appendChild(id);
        ul.appendChild(species);
        ul.appendChild(breed);
        ul.appendChild(gender);
        ul.appendChild(age);
        ul.appendChild(size);

        description.appendChild(ul);

        cardBody.appendChild(title);
        cardBody.appendChild(description);

        card.appendChild(image);
        card.appendChild(cardBody);

        container.appendChild(card);
    });
}


