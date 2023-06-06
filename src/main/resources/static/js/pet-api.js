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
            }else if (genderType && petType){
                apiUrl += `&gender=${genderType}&type=${petType}`;
            }else if (genderType && ageType) {
                apiUrl += `&gender=${genderType}&age=${ageType}`;
            }else if (petType && ageType){
                apiUrl += `&type=${petType}&age=${ageType}`;
            }else if (genderType){
                apiUrl += `&gender=${genderType}`;
            }else if(petType){
                apiUrl += `&type=${petType}`;
            }else if(ageType){
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
                    // Handle the API response here
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

    const genderTypeElement = document.querySelector('#gender-type .dropdown-items.active');
    if (genderTypeElement) {
        genderType = genderTypeElement.getAttribute('data-gender');
    }

    const ageTypeElement = document.querySelector('#age-type .dropdown-item.active');
    if (ageTypeElement) {
        ageType = ageTypeElement.getAttribute('data-age');
    }



    searchAnimalsByLocation(postalCode, petType, genderType, ageType);
});


//

