package servidor;

public class Corretora {

    private static final char[][] respostas = {
            {'V', 'F', 'F', 'V'},
            {'F', 'V', 'F', 'V'},
            {'V', 'F', 'F', 'V'},
            {'V', 'V', 'V', 'V'},
            {'F', 'F', 'F', 'V'},
            {'V', 'F', 'V', 'V'},
            {'F', 'V', 'V', 'V'},
            {'F', 'F', 'F', 'V'}
    };

    public static String analisando(String[] test) {
        String studentName = test[0];
        int wrongs = 0, rights = 0;
        for (int i = 1; i < respostas.length + 1; i++)
            for (int j = 2, k = 0; j <= 8; j = j + 2, k++)
                if (test[i].charAt(j) == respostas[i - 1][k]) {
                    rights++;
                } else wrongs++;

        return rights + " rights and " + wrongs + " wrongs" + " by " + studentName;
    }
}