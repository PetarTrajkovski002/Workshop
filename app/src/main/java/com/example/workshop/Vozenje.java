package com.example.workshop;

public class Vozenje
{
    public int Id;
    public int Korisnik_ID;
    public String Ime;
    public String Prezime;
    public double StartLat;
    public double StartLong;
    public double DestLat;
    public double DestLong;
    public int Status;

    public String Start_Place;
    public String Destination_Place;

   public Vozenje(int Id, int Korisnik_ID, String Ime, String Prezime, double StartLat, double StartLong, double DestLat, double DestLong, int Status, String Start_Place, String Destination_Place)
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
        this.Start_Place=Start_Place;
        this.Destination_Place=Destination_Place;
    }
}
