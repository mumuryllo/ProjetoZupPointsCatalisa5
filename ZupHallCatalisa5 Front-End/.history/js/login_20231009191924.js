document.addEventListener('DOMContentLoaded', function () {
    const loginForm = document.querySelector('#loginForm');

    loginForm.addEventListener('submit', function (e) {
        e.preventDefault();
        $('#cargoModal').modal('show');
    });

    document.querySelector('#confirmarCargo').addEventListener('click', function () {
        const usuario = document.querySelector('#usuario').value;
        const senha = document.querySelector('#senha').value;

        const selectedCargo = document.querySelector('#cargoSelect').value;

        const loginData = {
            username: usuario,
            password: senha,
        };

        fetch('http://localhost:8080/login/auth', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(loginData),
        })
        .then(response => response.json())
        .then(data => {
            console.log('Token de autenticação:', data.token);
            localStorage.setItem('token', data.token);
        })
        .catch(error => {
            console.error('Erro na autenticação:', error);
        });
    });

    const token = localStorage.getItem('token');

    if (!token) {
        console.error('Token de autenticação não encontrado.');
        return;
    }

    fetch('http://localhost:8080/usuarios', {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}` 
        },
    })
    .then(response => response.json())
    .then(data => {
        data.forEach(user => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${user.id}</td>
                <td>${user.nome}</td>
                <td>${user.username}</td>
            `;
            userList.appendChild(row);
        });
    })
    .catch(error => {
        console.log(error)
})
})