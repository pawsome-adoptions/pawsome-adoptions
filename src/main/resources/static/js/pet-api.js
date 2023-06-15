const clientId = [[${clientIDView}]];

const secret = [[${secretView}]];



petsByLocation(78245, "cat", "female", "adult");
function petsByLocation(postalCode, petType, genderType, ageType) {
    // Show loading GIF
    const container = document.getElementById('animalContainer');

    container.innerHTML = '<div class="d-flex justify-content-center text-light">\n' +
        '  <div class="spinner-border" role="status">\n' +
        '    <span class="visually-hidden">Loading...</span>\n' +
        '  </div>\n' +
        '</div>';
    // container.innerHTML = '<img src="/gifs/spinner-2.gif" alt="Loading" class="loading-gif">';
    // convert to Token
    fetch(`https://api.petfinder.com/v2/oauth2/token`, { // fetching the oAuth2 token from the Petfinder API
        method: `POST`,
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: `grant_type=client_credentials&client_id=${clientId}&client_secret=${secret}`,
    })
        .then(response => response.json())
        .then(token => {
            // handle the API response here
            let apiUrl = `https://api.petfinder.com/v2/animals?location=${postalCode}`;// updating the API URL based on the parameters

            //appending more paramaters based on users input from dropdowns
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

            //fetch data from the api
            fetch(apiUrl, { // getting data from the API using the URL and the access token
                method: `GET`,
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token.access_token}`
                }
            })
                .then(response => response.json())
                .then(data => {
                    container.innerHTML = '';// remove loading GIF once the pet cards load

                    petCards(data);//calling the function to display the pet cards with data

                    // //call the pageNums function to display page numbers
                    // pageNums(apiUrl, data);
                })
                .catch(error => {
                    console.error(error)
                    window.location.href = 'error-page2.html'; //redirecting error-page2 if there is an error
                });
        })
        .catch(error => {
            // handle any errors that occurred during the request
            console.error(error);
            window.location.href = '/error-page2.html';//redirecting error-page2 if there is an error
        });
}


// get user input from a search form
const petSearch = document.getElementById('petSearch');
const searchInput = document.getElementById('search-bar');
const animalType = document.getElementById('type');
const genderType = document.getElementById('gender-type');
const ageType = document.getElementById('age-type');

//event listener for animal type(dropdown)
animalType.addEventListener('click', e => { //added a click event listener
    e.preventDefault(); //stop default behavior

    //the idea is the loop keeps going up the tree until it finds the  element

    let animalPicked = e.target;//retrieves 'data-type' of what the user clicked on and assigns it to animal picked
    while (animalPicked && !animalPicked.classList.contains('dropdown-item')) {
        animalPicked = animalPicked.parentElement; //this moves it up the DOM tree if found
    }


    //this is to visually show what was picked from the dropdown
    //the foreach removes the active class to make sure it's not highlighted
    //and the dropdown the user picked gets the active class added to it

    if (animalPicked) {// true, if the user clicked something in the dropdown
        animalType.querySelectorAll('.dropdown-item').forEach(option => {
            option.classList.remove('active');// remove the class active from each dropdown item
        });

        animalPicked.classList.add('active');// add the class active to the selected dropdown item

        // console.log(animalPicked.dataset.type);
    }
});

//event listener for gender type
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

//event listener for age type
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

//event listener for pet search
petSearch.addEventListener('submit', e => {
    e.preventDefault();

    const postalCode = searchInput.value; //gets the value of what the user entered in the search
    let petType = null; //variable to put the selected type
    let genderType = null; //variable to put the selected type
    let ageType = null; //variable to put the selected type

    // gets the active dropdown item from the dropdown menu
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

    // calls the petsByLocation
    petsByLocation(postalCode, petType, genderType, ageType);
});


//displaying animals
function petCards(data) {
    const container = document.getElementById('animalContainer');
    container.innerHTML = '';

    // check if the its null, make sure it's an object,  is valid and contains animals
    if (!data || typeof data !== 'object') {
        const noResults = document.createElement('p');
        noResults.textContent = 'No animals found.';
        container.appendChild(noResults);
        return;
    }

    //gets the array of animals from the JSON data
    const animals = data.animals;

    // make sure its an array and the length
    if (!Array.isArray(animals) || animals.length === 0) {
        const noResults = document.createElement('p');
        noResults.textContent = 'No animals found.';
        container.appendChild(noResults);
        return;
    }

    //make pet cards (for each) animal and append them to the container
    animals.forEach(animal => {
        const card = document.createElement('div');
        card.classList.add('card', 'mx-auto', 'my-4', 'col-12', 'col-sm-6', 'col-md-4', 'col-lg-3', 'col-xl-3', 'col-xxl-screens', 'hvr-grow', 'bg-boxshadow');
        card.style.width = '20rem';

        const image = document.createElement('img');
        image.classList.add('card-img-top', 'mt-3', 'd-flex', 'justify-content-center' , 'align-items-center');
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

        const button = document.createElement('button');
        button.classList.add('btn', 'btn-primary', 'hvr-pulse-shrink');
        button.textContent = 'View Details';
        button.setAttribute('data-bs-toggle', 'modal');
        button.setAttribute('data-bs-target', '#myModal');

        button.addEventListener('click', () => {
            showModal(animal);
        });

        cardBody.appendChild(title);
        cardBody.appendChild(description);
        cardBody.appendChild(button);

        card.appendChild(image);
        card.appendChild(cardBody);

        container.appendChild(card);
    });
}

// Display Modal
function showModal(animal) {
    const modalTitle = document.getElementById('modalTitle');
    const modalImage = document.getElementById('modalImage');
    const modalDescription = document.getElementById('modalDescription');

    modalTitle.innerHTML = '';
    modalImage.src = '';
    modalDescription.textContent = '';
    //make modal content
    const title = document.createElement('h5');
    title.classList.add('card-title');
    title.textContent = animal.name;

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

    //put the info in the modal
    modalTitle.appendChild(title);
    modalDescription.appendChild(ul);

    //modal image source
    modalImage.src = animal.photos.length > 0 ? animal.photos[0].large : '/img/img_not_found_wide.jpg';
    modalImage.alt = 'Animal Image';

    const myModal = new bootstrap.Modal(document.getElementById('myModal'));
    myModal.show();

    // hide the background and allow scrolling
    myModal._element.addEventListener('hidden.bs.modal', function () {
        const modalBackdrop = document.querySelector('.modal-backdrop');
        if (modalBackdrop) {
            modalBackdrop.remove();
        }
        document.body.classList.remove('modal-open');
        document.body.style.overflow = 'auto'; // restore scrolling
    });
}

//function for pageNums
// function pageNums(apiUrl, data) {
//     let paginationElement = document.getElementById('pagination');
//
//     paginationElement.innerHTML = '';
//
//     // get the current page and the # for the rest of the pages
//     const currentPage = data.pagination.current_page;
//     const totalPages = data.pagination.total_pages;
//
//     // create new anchor tags with a for loop
//     for (let i = 1; i <= totalPages; i++) {
//         let anchorElement = document.createElement('a'); //create anchor
//         anchorElement.href = `${apiUrl}&page=${i}`; // set the page #
//         anchorElement.textContent = i; // html text
//         console
//
//         if (i === currentPage) {
//             anchorElement.classList.add('active'); // highlight what page user is on
//         }
//
//         paginationElement.appendChild(anchorElement); // append the anchor tag to the page element
//     }
// }



