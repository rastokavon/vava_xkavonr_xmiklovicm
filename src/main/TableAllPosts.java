package main;

/**
 * Trieda, ktora obsahuje atributy, ktorymi sa naplnaju tabulky, konkretne tabulky otvoreneho prispevku,
 * kde su jednotlive komentare v tabulke, ale taktiez tabulka vsetkych prispevkov v hlavnom menu pouzivatela
 * po prihlaseni alebo v okno rozkliknuteho prihlaseneho pouzivatela
 *
 * Atributmi su 2 stlce v tabulke: v prvom je nadpis prispevku (resp. text komentaru), v druhom je datum a meno
 * pouzivatela, ktory komentar pridal.
 */
public class TableAllPosts {
    private String title;
    private String dateName;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDateName() {
        return dateName;
    }

    public void setDateName(String dateName) {
        this.dateName = dateName;
    }

}
