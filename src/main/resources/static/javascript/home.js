
//Cookie
const cookieArr = document.cookie.split("=")
const userId = cookieArr[1];
if (!userId) window.location.replace("http://localhost:8080/login.html");
console.log(userId)

//DOM Elements
const submitForm = document.getElementById("note-form")
const noteContainer = document.getElementById("note-container")

const searchForm = document.getElementById("search-form")
//const searchContainer = document.getElementById("search-container")

//Modal Elements
let noteBody = document.getElementById(`note-body`)
let updateNoteBtn = document.getElementById('update-note-button')

const headers = {
    'Content-Type': 'application/json'
}

const baseUrl = "http://localhost:8080/api/v1/notes/"

const toBase64 = file => new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => resolve(reader.result);
    reader.onerror = reject;
});


const handleSearchSubmit = async (e) => {
     e.preventDefault()
     let body= document.getElementById("search-input").value.trim()
     getNotesByBody(body)

     document.getElementById("search-input").value = ''
}

const handleSubmit = async (e) => {
    e.preventDefault()

    let file = document.getElementById("customFile").files[0]
    let imageData = ''

    if (file) {
        try {
            imageData = await toBase64(file)
        }
        catch (err) {
            console.log(err)
        }
    }

    let bodyObj = {
                body: document.getElementById("note-input").value,
                imageData : imageData
    }
    addNote(bodyObj)

    document.getElementById("note-input").value = ''  
}



async function addNote(obj) {
    const response = await fetch(`${baseUrl}user/${userId}`, {
        method: "POST",
        body: JSON.stringify(obj),
        headers: headers
    })
        .catch(err => console.error(err.message))
    if (response.status == 200) {
        return getNotes(userId);
    }
}

async function getNotes(userId) {
    await fetch(`${baseUrl}user/${userId}`, {
        method: "GET",
        headers: headers
    })
        .then(response => response.json())
        .then(data => createNoteCards(data))
        .catch(err => console.error(err))
}

async function getNotesByBody(body='') {
    await fetch(`${baseUrl}user/body/${userId}`, {
        method: "POST",
        body: body,
        headers: headers
    })
        .then(response => response.json())
        .then(data => createNoteCards(data))
        .catch(err => console.error(err))
}

async function handleDelete(noteId){
    await fetch(baseUrl + noteId, {
        method: "DELETE",
        headers: headers
    })
        .catch(err => console.error(err))

    return getNotes(userId);
}

async function getNoteById(noteId){
    await fetch(baseUrl + noteId, {
        method: "GET",
        headers: headers
    })
        .then(res => res.json())
        .then(data => populateModal(data))
        .catch(err => console.error(err.message))
}

async function handleNoteEdit(noteId){
    let bodyObj = {
        id: noteId,
        body: noteBody.value
    }

    await fetch(baseUrl, {
        method: "PUT",
        body: JSON.stringify(bodyObj),
        headers: headers
    })
        .catch(err => console.error(err))

    return getNotes(userId);
}

const createNoteCards = (array) => {
    noteContainer.innerHTML = ''
    if (!array.length) return
    array.forEach(obj => {
        let noteCard = document.createElement("div")
        noteCard.classList.add("m-0")
        noteCard.innerHTML = `
            <div class="card d-flex" ">
                <div class="card-body d-flex flex-column  justify-content-between" style="height: available">
                    <p class="card-text">${obj.body}</p>
                    <img src="${obj.imageData}" class="card-img"/>
                    <div class="d-flex justify-content-between">
                        <button class="btn btn-danger" onclick="handleDelete(${obj.id})">Delete</button>
                        <button onclick="getNoteById(${obj.id})" type="button" class="btn btn-primary" 
                        data-bs-toggle="modal" data-bs-target="#note-edit-modal">
                        Edit
                        </button>
                    </div>
                </div>
            </div>
        `
        noteContainer.append(noteCard);
    })
}
function handleLogout(){
    let c = document.cookie.split(";");
    for(let i in c){
        document.cookie = /^[^=]+/.exec(c[i])[0]+"=;expires=Thu, 01 Jan 1970 00:00:00 GMT"
    }
}

const populateModal = (obj) =>{
    noteBody.innerText = ''
    noteBody.innerText = obj.body
    updateNoteBtn.setAttribute('data-note-id', obj.id)
}

getNotes(userId);

submitForm.addEventListener("submit", handleSubmit)

searchForm.addEventListener("submit", handleSearchSubmit)

updateNoteBtn.addEventListener("click", (e)=>{
    let noteId = e.target.getAttribute('data-note-id')
    handleNoteEdit(noteId);
})