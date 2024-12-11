// scripts.js

const API_BASE_URL = 'http://localhost:8080/api'; // Замените на ваш адрес сервера

// Функция для выполнения GET-запроса
async function getData(endpoint) {
    try {
        const response = await fetch(`${API_BASE_URL}/${endpoint}`);
        if (!response.ok) {
            throw new Error(`Ошибка: ${response.status}`);
        }
        return await response.json();
    } catch (error) {
        console.error(error);
        alert(`Не удалось получить данные: ${error.message}`);
    }
}

// Функция для выполнения POST-запроса
async function postData(endpoint, data) {
    try {
        const response = await fetch(`${API_BASE_URL}/${endpoint}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        });
        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.error || `Ошибка: ${response.status}`);
        }
        return await response.json();
    } catch (error) {
        console.error(error);
        alert(`Не удалось создать запись: ${error.message}`);
    }
}

// Функция для выполнения PUT-запроса
async function putData(endpoint, data) {
    try {
        const response = await fetch(`${API_BASE_URL}/${endpoint}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        });
        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.error || `Ошибка: ${response.status}`);
        }
        return await response.json();
    } catch (error) {
        console.error(error);
        alert(`Не удалось обновить запись: ${error.message}`);
    }
}

// Функция для выполнения DELETE-запроса
async function deleteData(endpoint) {
    try {
        const response = await fetch(`${API_BASE_URL}/${endpoint}`, {
            method: 'DELETE'
        });
        if (!response.ok) {
            throw new Error(`Ошибка: ${response.status}`);
        }
        return true;
    } catch (error) {
        console.error(error);
        alert(`Не удалось удалить запись: ${error.message}`);
    }
}
