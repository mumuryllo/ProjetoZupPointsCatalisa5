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

            const token = localStorage.getItem('token');

            fetch('http://localhost:8080/usuarios', {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${token}`
                },
            })
            .then(response => response.json())
            .then(userList => {
                console.log(userList);

                const loggedInUser = userList.find(user => user.username === usuario);

                if (loggedInUser) {
                    const userRole = loggedInUser.role;

                    if (userRole === 'ROLE_COLABORADOR' && userRole === 'ROLE_GESTOR') {
                        console.error('Usuário tem múltiplas funções. Especifique uma função.');
                    } else if (userRole === 'ROLE_COLABORADOR') {
                        window.location.href = 'colaborador.html';
                    } else if (userRole === 'GESTOR') {
                        window.location.href = 'gestor.html';
                    } else {
                        console.error('Função de usuário desconhecida:', userRole);
                    }
                } else {
                    console.error('Usuário não encontrado.');
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