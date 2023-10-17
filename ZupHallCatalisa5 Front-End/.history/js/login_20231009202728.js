document.addEventListener('DOMContentLoaded', function () {
    const loginForm = document.querySelector('#loginForm');

    loginForm.addEventListener('submit', function (e) {
        e.preventDefault();

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

            // Use o token de autenticação para obter as informações do usuário
            const token = localStorage.getItem('token');

            fetch('http://localhost:8080/usuarios', {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${token}`
                },
            })
            .then(response => response.json())
            .then(userInfo => {
                console.log(userInfo);

                // Obtenha a função (role) do usuário do servidor
                const userRole = userInfo[];
                console.log(userRole);

                // Agora você pode usar userRole para tomar decisões de redirecionamento
                if (userRole === 'ROLE_COLABORADOR') {
                    window.location.href = 'colaborador.html';
                } else if (userRole === 'ROLE_GESTOR') {
                    window.location.href = 'gestor.html';
                } else {
                    console.error('Função de usuário desconhecida:', userRole);
                }
            })
            .catch(error => {
                console.error('Erro ao obter informações do usuário:', error);
            });
        })
        .catch(error => {
            console.error('Erro na autenticação:', error);
        });
    });
});