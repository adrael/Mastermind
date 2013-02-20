/**
 * Created with IntelliJ IDEA.
 * User: Jérémie Mercuri
 * Date: 20/02/13
 * Time: 14:41
 * cette classe gère fonctinemment général du jeu
 */
import java.awt.*;
import java.util.Random;

public class Mastermind {
    private PawnGame solution; //combinaison générée par la fonction generate
    private PawnGame player;   //combinaison du joueur
    private int nbTry;         //nombres d'essais du joueur
    private int nbTryMax;      //nombres d'essais maximum
    private int nbColor;       //nombres de couleurs
    private boolean result;    //resultat de la comparaison entre la combinaison du joueur et la solution
    private Color tabColor[]={Color.red,Color.yellow,Color.green,Color.blue,Color.white,Color.black};

    public Mastermind(int trys, int color)
    {
        nbTryMax = trys;
        nbColor = color;
        nbTry = 0;
        result = false;
    }

    public void generate()
    {
         solution = new PawnGame();
        Random r = new Random();
        int n;
        for(int i=0; i<nbColor; i++)
        {
            n = r.nextInt(6);
            solution[i]=tabColor[n];
        }
    }
}


/*
import java.awt.*;
import java.util.Random;

public class Mastermind
{
    private Color laSolution[];
    private Color joueurSolution[];
    private Color tabColor[]={Color.red,Color.yellow,Color.green,Color.blue,Color.white,Color.black};
    private int nbCouleur;
    private int nbEssais;
    private int nbEssaisMAx;
    private boolean resu;

    public Mastermind(int unNbEssaisMax, int unNbCouleur)
    {
        nbEssaisMAx = unNbEssaisMax;
        nbEssais = 0;
        nbCouleur = unNbCouleur;
        resu = false;
    }

    public void genererSolution() //mÃ©thode permÃ©tant de gÃ©nÃ©rer une solution
    {
        laSolution = new Color[nbCouleur];
        Random r = new Random();
        int n;
        for(int i=0;i<nbCouleur;i++)
        {
            n = r.nextInt(6);
            laSolution[i]=tabColor[n];
        }
    }

    public void setJoueurSolution(Color[] unTabColor)
    {
        joueurSolution = unTabColor;
    }

    public void setNbEssais() //incrÃ©mente le nombre d'Ã©ssais de 1
    {
        nbEssais++;
    }

    public int getBienPlace() //retourne le nombre de couleurs bien placÃ©es
    {
        int nbBien=0;

        for(int i=0;i<nbCouleur;i++)
        {
            if (joueurSolution[i]==laSolution[i]) nbBien++;
        }
        if (nbBien==nbCouleur) resu=true;
        return nbBien;
    }

    public int getMalPlace() //return le nombre de couleurs mal placÃ©es
    {
        int nbMal=0;
        int k=0;
        Color joueurCombi[];
        joueurCombi = new Color[nbCouleur];
        Color laCombi[];
        laCombi = new Color[nbCouleur];

        for(int i=0;i<nbCouleur;i++)
        {
            if(laSolution[i] != joueurSolution[i])
            {
                laCombi[k] = laSolution[i];
                joueurCombi[k] = joueurSolution[i];
                k++;
            }
        }
        for(int i=0;i<k;i++)
        {
            int j=0;
            while(j<k)
            {
                if(joueurCombi[i] == laCombi[j])
                {
                    nbMal++;
                    laCombi[j] = null;
                    j=k+1;
                }
                else
                {
                    j++;
                }
            }
        }
        return nbMal;
    }

    public void init()
    {
        nbEssais=0;
        resu=false;
    }

    public int getNbCouleur()
    {
        return nbCouleur;
    }

    public int getNbEssaisMax()
    {
        return nbEssaisMAx;
    }

    public int getNbEssais()
    {
        return nbEssais;
    }

    public boolean getResu()
    {
        return resu;
    }

    public Color getSolution(int i)
    {
        return laSolution[i];
    }
}*/