const url = 'http://localhost:8080/api/users';
const userUrl = 'http://localhost:8080/api/users/auth';
let users;
let authUser;

async function updatePage() {
    users = await getUsers();
    authUser = await getAuthUser();
    fillTable(users);
    fillUserPage(authUser);
}

async function getUsers() {
    const respose = await fetch(url);
    const usersJson = await respose.json();
    const users = JSON.stringify(usersJson);
    return JSON.parse(users);
}

async function getAuthUser() {
    const response = await fetch(userUrl);
    if (!response.ok) {
        console.error('Error fetching authenticated user:', response.statusText);
        return null; 
    }
    const authUserJson = await response.json();
    return authUserJson;
}

function fillTable( users ) {
    const table = document.getElementById('usersTable');
    table.innerHTML = '';

    for ( let user of users ) {
        const row = document.createElement("tr");
        addUserInfo( row, user );
        addButtons( row, user );
        table.appendChild( row );
    }
};

function fillUserPage(user) {
    const row = document.getElementById('userPageInfo');
    row.innerHTML = '';
    addUserInfo(row, user);
}

function addUserInfo(row, user) {
    const idCell = document.createElement("td");
    idCell.textContent = user.id;
    row.appendChild(idCell);

    const firstNameCell = document.createElement("td");
    firstNameCell.textContent = user.firstName;
    row.appendChild(firstNameCell);

    const lastNameCell = document.createElement("td");
    lastNameCell.textContent = user.lastName;
    row.appendChild(lastNameCell);

    const ageCell = document.createElement("td");
    ageCell.textContent = user.age;
    row.appendChild(ageCell);

    const emailCell = document.createElement("td");
    emailCell.textContent = user.email;
    row.appendChild(emailCell);

    const rolesCell = document.createElement("td");
    rolesCell.textContent = user.roles.map(role => role.roleName).join(', ');
    row.appendChild(rolesCell);
}

function addButtons(row, user) {
    const editButton = document.createElement('td');
    editButton.innerHTML = `<button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#modalEditUser"
                                data-user-id="${user.id}"
                                data-user-firstName="${user.firstName}"
                                data-user-lastName="${user.lastName}"
                                data-user-age="${user.age}"
                                data-user-email="${user.email}"
                                data-user-roles="${user.roles.map(role => role.roleName).join(',')}"
                                >Edit</button>`;
    editButton.addEventListener('click', (event) => fillEditUserMonad(event))
    row.appendChild(editButton);

    const deleteButton = document.createElement('td');
    deleteButton.innerHTML = `<button type="button" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#modalDeleteUser"
                                  data-user-id="${user.id}"
                                  data-user-firstName="${user.firstName}"
                                  data-user-lastName="${user.lastName}"
                                  data-user-age="${user.age}"
                                  data-user-email="${user.email}"
                                  data-user-roles="${user.roles.map(role => role.roleName).join(',')}"
                                  >Delete</button>`;
    deleteButton.addEventListener('click', (event) => fillDeleteUserMonad(event))
    row.appendChild(deleteButton);
}

function fillEditUserMonad( event ) {
    const userId = event.target.dataset.userId;
    const firstName = event.target.dataset.userFirstname;
    const lastName = event.target.dataset.userLastname;
    const age = event.target.dataset.userAge;
    const email = event.target.dataset.userEmail;
    const roles = event.target.dataset.userRoles;

    const editUserIdDisplay = document.getElementById('editUserIdDisplay');
    const editFirstName = document.getElementById('editFirstName');
    const editLastName = document.getElementById('editLastName');
    const editAge = document.getElementById('editAge');
    const editEmail = document.getElementById('editEmail');
    const editRoles = document.getElementById('editRoles');

    editUserIdDisplay.value = userId;
    editFirstName.value = firstName;
    editLastName.value = lastName;
    editAge.value = age;
    editEmail.value = email;

    const selectedRoles = roles.split(',');
    const option = editRoles.querySelectorAll('option');
    option.forEach(op => {
        if(selectedRoles.includes(op.textContent)){
            op.selected = true;
        } else {
            op.selected = false;
        }
    });
}


function fillDeleteUserMonad( event ) {
    const userId = event.target.dataset.userId;
    const firstName = event.target.dataset.userFirstname;
    const lastName = event.target.dataset.userLastname;
    const age = event.target.dataset.userAge;
    const email = event.target.dataset.userEmail;
    const roles = event.target.dataset.userRoles;

    const editUserIdDisplay = document.getElementById('deleteUserIdDisplay');
    const editFirstName = document.getElementById('deleteFirstName');
    const editLastName = document.getElementById('deleteLastName');
    const editAge = document.getElementById('deleteAge');
    const editEmail = document.getElementById('deleteEmail');
    const editRoles = document.getElementById('deleteRoles');

    editUserIdDisplay.value = userId;
    editFirstName.value = firstName;
    editLastName.value = lastName;
    editAge.value = age;
    editEmail.value = email;
    const selectedRoles = roles.split(',');
    const option = editRoles.querySelectorAll('option');
    option.forEach(op => {
        if(selectedRoles.includes(op.textContent)){
            op.selected = true;
        } else {
            op.selected = false;
        }
    });
}

function createButtonListener() {
    const editModalForm = document.getElementById('editButton');
    addListenerToEditModal( editModalForm )
    const deleteModalForm = document.getElementById('deleteButton');
    addListenerToDeleteModal( deleteModalForm )
}

function addListenerToEditModal( editModalForm ) {
    editModalForm.addEventListener('click', async (event) => {
        event.preventDefault();

        const userId = document.getElementById('editUserIdDisplay').value;
        const firstName = document.getElementById('editFirstName').value;
        const lastName = document.getElementById('editLastName').value;
        const age = document.getElementById('editAge').value;
        const email = document.getElementById('editEmail').value;
        const password = document.getElementById('editPassword').value;
    
        const roleSelect = document.getElementById('editRoles');
        const selectedRoles = Array.from(roleSelect.selectedOptions).map(option => ({
            id: parseInt(option.value, 10),
            roleName: option.textContent,
        }));
    
        const user = {
            id: userId,
            firstName: firstName,
            lastName: lastName,
            age: age,
            email: email,
            password: password,
            roles: selectedRoles
        };
        await updateUser(user);
        const modal = bootstrap.Modal.getInstance(document.getElementById("modalEditUser"));
        modal.hide();
    })
}
async function updateUser(user) {
    const response = await fetch(url, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': csrfToken,
        },
        body: JSON.stringify(user),
    });
    if (!response.ok) {
        console.error('Error updating user:', response.statusText);
    } else {
        updatePage();
    }
}


function addListenerToDeleteModal( deleteModalForm ) {
    deleteModalForm.addEventListener('click', async (event) => {
        event.preventDefault();
    
        const userId = document.getElementById('deleteUserIdDisplay').value;
    
        await deleteUser(userId);
        const modal = bootstrap.Modal.getInstance(document.getElementById("modalDeleteUser"));
        modal.hide();
    });
}
async function deleteUser(userId) {
    const response = await fetch(`${url}/${userId}`, {
        method: 'DELETE',
        'X-CSRF-TOKEN': csrfToken,
    });
    if (!response.ok) {
        console.error('Error deleting user:', response.statusText);
    } else {
        updatePage();
    }
}

updatePage()
createButtonListener()