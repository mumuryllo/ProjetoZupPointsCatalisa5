document.addEventListener('DOMContentLoaded', function () {
    const loginForm = document.querySelector('#loginForm');

    loginForm.addEventListener('submit', function (e) {
        e.preventDefault();

        $('#cargoModal').modal('show');
    });

    document.querySelector('#confirmarCargo').addEventListener('click', function () {
        const usuario = document.querySelector('#usuario').value;
        const senha = document.querySelector('#senha').value;

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

            // Após autenticar o usuário, faça uma segunda requisição para obter as informações de autorização
            fetch('http://localhost:8080/usuario/informacoes', {
                method: 'GET',
                headers: {
                    Authorization: 'Bearer ' + data.token,
                },
            })
            .then(response => response.json())
            .then(userInfo => {
                // Verificar a autorização do usuário e redirecionar para a página apropriada
                if (userInfo.role === 'ROLE_COLABORADOR') {
                    window.location.href = 'colaborador.html';
                } else if (userInfo.role === 'ROLE_GESTOR') {
                    window.location.href = 'gestor.html';
                } else {
                    console.error('Função de usuário desconhecida:', userInfo.role);
                }
            })
            .catch(error => {
                console.error('Erro ao obter informações de autorização:', error);
            });
        })
        .catch(error => {
            console.error('Erro na autenticação:', error);
        });
    });
});