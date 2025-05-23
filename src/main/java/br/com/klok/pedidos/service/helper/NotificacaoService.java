package br.com.klok.pedidos.service.helper;

import org.springframework.stereotype.Service;

@Service
public class NotificacaoService {
    public void enviar(String email, String mensagem) {
        System.out.printf("Enviando e-mail para %s: %s%n", email, mensagem);
    }
}
