document.addEventListener('DOMContentLoaded', function () {
    const loginForm = document.querySelector('#loginForm');

    loginForm.addEventListener('submit', function (e) {
        e.preventDefault();

        // Abre o modal de seleção de cargo
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

            if (selectedCargo === 'ROLE_COLABORADOR') {
                window.location.href = 'colaborador.html';
            } else if (selectedCargo === 'ROLE_GESTOR') {
                window.location.href = 'gestor.html';
            } else {
                console.error('Função de usuário desconhecida:', selectedCargo);
            }
        })
        .catch(error => {
            console.error('Erro na autenticação:', error);
        });
    });
});