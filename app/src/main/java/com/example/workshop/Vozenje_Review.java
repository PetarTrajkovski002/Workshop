package com.example.workshop;

public class Vozenje_Review {
    public int Id;
    public int Korisnik_ID;
    public String Ime;
    public String Prezime;
    public double StartLat;
    public double StartLong;
    public double DestLat;
    public double DestLong;
    public int Status;
    public String Start;
    public String End;

    public String Slika;

    public String Model;

    public int Godina;

    public String Komentar;

    public int Rating;
    public Vozenje_Review(int Id, int Korisnik_ID, String Ime, String Prezime, double StartLat, double StartLong, double DestLat, double DestLong, int Status, String Start, String End, String Slika, String Model, int Godina, String Komentar, int Rating)
    {
        this.Id=Id;
        this.Ime=Ime;
        this.Korisnik_ID=Korisnik_ID;
        this.Prezime=Prezime;
        this.StartLat=StartLat;
        this.StartLong=StartLong;
        this.DestLat=DestLat;
        this.DestLong=DestLong;
        this.Status=Status;
        this.Start=Start;
        this.End=End;
        this.Slika=Slika;
        this.Model=Model;
        this.Godina=Godina;
        this.Komentar=Komentar;
        this.Rating=Rating;
    }

}
