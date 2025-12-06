const form = document.querySelector("form");
const input = document.querySelector("input");
const button = document.querySelector("button");
const resultDiv = document.getElementById("result");
const resultText = document.getElementById("result-text");
const copyBtn = document.getElementById("copy-btn");

const shortBtn = document.getElementById("short-btn")

shortBtn.addEventListener("click", async (event) => {

    let value = input.value.trim();

    if (value === "") {
        alert("URL não pode ser vazia");
        return;
    }

    if (value.includes(" ")) {
        alert("URL não pode conter espaços");
        return;
    }

    if (!value.startsWith("http://") && !value.startsWith("https://")) {
        value = "https://" + value;
    }

    // Validar URL
    try {
        new URL(value);
    } catch (error) {
        alert("URL inválida");
        console.log(error);
        return;
    }

    // Desabilitar botão durante a requisição
    button.disabled = true;
    button.textContent = "Encurtando...";

    try {
        // Fazer requisição para o backend - Aciona a API
        const response = await fetch('/shorten-url', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                url: value
            })
        });
        
            
        if (response.ok) {
            const data = await response.json();

            // Mostrar resultado na página
            resultText.innerHTML = `URL encurtada com sucesso!<br><span class="short-url">${data.url}</span>`;
            resultDiv.style.display = "block";

            // Limpar campo de entrada
            input.value = "";

            // Scroll para o resultado
            resultDiv.scrollIntoView({ behavior: 'smooth' });

            // Ajuste span-class
            
        } else {
            resultText.innerHTML = "Erro ao encurtar a URL. Tente novamente.";
            resultDiv.style.display = "block";
        }
    } catch (error) {
        console.error("Erro:", error);
        alert("Erro de conexão. Verifique se o servidor está rodando.");
    } finally {
        // Reabilitar botão
        button.disabled = false;
        button.textContent = "Encurtar";
    }
});

// Função para copiar URL
copyBtn.addEventListener("click", async (event) => {
    event.preventDefault()
    const shortUrlElement = document.querySelector(".short-url");
    if (shortUrlElement) {
        const url = shortUrlElement.textContent;
        try {
            await navigator.clipboard.writeText(url);
            copyBtn.textContent = "Copiado!";
            copyBtn.style.backgroundColor = "#10B981";

            setTimeout(() => {
                copyBtn.textContent = "Copiar";
                copyBtn.style.backgroundColor = "#8cf5a6ff";
            }, 2000);
        } catch (error) {
            // Fallback para naveagdores antigos
            const textArea = document.createElement("textarea");
            textArea.value = url;
            document.body.appendChild(textArea);
            textArea.select();
            document.execCommand("copy");
            document.body.removeChild(textArea);

            copyBtn.textContent = "Copiado!";
            copyBtn.style.backgroundColor = "#10B981";

            setTimeout(() => {
                copyBtn.textContent = "Copiar";
                copyBtn.style.backgroundColor = "#8cf5a6ff";
            }, 2000);
        }
    }
});

