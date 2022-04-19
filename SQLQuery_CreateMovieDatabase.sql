create database MovieDatabase
go

use MovieDatabase
go

create table Movie (
	IdMovie int primary key identity,
	Title nvarchar(50) not null,
	PublishedDate nvarchar(90) not null,
	Description nvarchar(max) not null,
	OriginalTitle nvarchar(50) not null,
	Duration int not null,
	Year int not null,
	Genre nvarchar(50) not null,
	ImagePath nvarchar(100) not null,
	Rating int not null,
	Type nvarchar(50),
	PageLink nvarchar(100) not null,
	ReservationLink nvarchar(100),
	StartDate nvarchar(90)
)
go

create table Actor (
	IdActor int primary key identity,
	FirstName nvarchar(50) not null,
	LastName nvarchar(50)
)
go

create table Director (
	IdDirector int primary key identity,
	FirstName nvarchar(50) not null,
	LastName nvarchar(50)
)
go

create table MovieActor (
	MovieId int foreign key references Movie(IdMovie),
	ActorId int foreign key references Actor(IdActor)
)
go

create table MovieDirector (
	MovieId int foreign key references Movie(IdMovie),
	DirectorId int foreign key references Director(IdDirector)
)
go


--ACTOR CRUD


create proc createActor
	@FirstName nvarchar(50),
	@LastName nvarchar(50),
	@IdActor int output
as
begin
	insert into Actor (FirstName, LastName) values (@FirstName, @LastName)
	set @IdActor = SCOPE_IDENTITY()
end
go

create proc selectActors 
as
begin 
	select * 
	from Actor
end
go

create proc selectActor
	@IdActor int
as
begin 
	select * 
	from Actor
	where IdActor = @IdActor
end
go

create proc selectActedMovies 
	@IdActor int
as
begin
	select *
	from Movie as m
	inner join MovieActor as md on m.IdMovie = md.MovieId
	where md.ActorId = @IdActor
end
go

create proc deleteActedMovies 
	@IdActor int
as
begin
	delete from  MovieActor where ActorId = @IdActor
end
go

create proc updateActor
	@FirstName nvarchar(50),
	@LastName nvarchar(50),
	@idActor int
as
begin
	update Actor set
		FirstName = @FirstName,
		LastName = @LastName
	where IdActor = @idActor
end
go

create proc deleteActor
	@IdActor int
as
begin
	delete from MovieActor where ActorId = @IdActor
	delete from Actor where IdActor = @IdActor
end
go




--DIRECTOR CRUD


create proc createDirector
	@FirstName nvarchar(50),
	@LastName nvarchar(50),
	@IdDirector int output
as 
begin
	insert into Director (FirstName, LastName) values (@FirstName, @LastName)
	set @IdDirector = SCOPE_IDENTITY();
end
go

create proc selectDirectors
as
begin
	select *
	from Director
end
go

create proc selectDirector 
	@IdDirector int
as
begin
	select *
	from Director
	where IdDirector =  @IdDirector
end
go

create proc selectDirectedMovies 
	@IdDirector int
as
begin
	select *
	from Movie as m
	inner join MovieDirector as md on m.IdMovie = md.MovieId
	where md.DirectorId = @IdDirector
end
go

create proc deleteDirectedMovies 
	@IdDirector int
as
begin
	delete from MovieDirector where DirectorId = @IdDirector
end
go

create proc updateDirector 
	@FirstName nvarchar(50),
	@LastName nvarchar(50),
	@IdDirector int output
as 
begin
	update Director set
		FirstName = @FirstName,
		LastName = @LastName
	where IdDirector = @IdDirector
end
go


create proc deleteDirector
	@IdDirector int
as
begin
	delete from MovieDirector where DirectorId = @IdDirector
	delete from Director where IdDirector = @IdDirector
end
go

--MOVIE CRUD

create proc createMovie 
	@Title nvarchar(50),
	@PublishedDate nvarchar(90),
	@Description nvarchar(max),
	@OriginalTitle nvarchar(50),
	@Duration int,
	@Year int,
	@Genre nvarchar(50),
	@ImagePath nvarchar(100),
	@Rating int,
	@Type nvarchar(50),
	@PageLink nvarchar(100),
	@ReservationLink nvarchar(100),
	@StartDate nvarchar(90),
	@IdMovie int output
as 
begin
	insert into Movie (Title, PublishedDate, Description, OriginalTitle, Duration, Year, Genre, ImagePath, Rating, Type, PageLink, ReservationLink, StartDate)
		values (@Title, @PublishedDate, @Description, @OriginalTitle, @Duration, @Year, @Genre, @ImagePath, @Rating, @Type, @Pagelink, @ReservationLink, @StartDate)
	set @IdMovie = SCOPE_IDENTITY();
end
go

create proc selectMovie 
	@IdMovie int
as
begin
select * 
from Movie 
where IdMovie = @IdMovie
end
go

create proc selectMovies 
as
begin
select * 
from Movie 
end
go

create proc selectMovieActors
	@IdMovie int
as
begin 
	select a.*
	from Actor as a
	inner join MovieActor as ma on a.IdActor = ma.ActorId
	inner join Movie as m on ma.MovieId = m.IdMovie
	where m.IdMovie = @IdMovie
end
go

create proc selectMovieDirectors
	@IdMovie int
as
begin 
	select d.*
	from Director as d
	inner join MovieDirector as md on d.IdDirector = md.DirectorId
	inner join Movie as m on md.MovieId = m.IdMovie
	where m.IdMovie = @IdMovie
end
go

create proc updateMovie
	@Title nvarchar(50),
	@PublishedDate nvarchar(90),
	@Description nvarchar(max),
	@OriginalTitle nvarchar(50),
	@Duration int,
	@Year int,
	@Genre nvarchar(50),
	@ImagePath nvarchar(100),
	@Rating int,
	@Type nvarchar(50),
	@PageLink nvarchar(100),
	@ReservationLink nvarchar(100),
	@StartDate nvarchar(90),
	@IdMovie int output
as 
begin
	update Movie set
		Title = @Title,
		PublishedDate = @PublishedDate,
		Description = @Description,
		OriginalTitle = @OriginalTitle,
		Duration = @Duration,
		Year = @Year,
		Genre = @Genre,
		ImagePath = @ImagePath,
		Rating = @Rating,
		Type = @Type,
		Pagelink = @Pagelink,
		ReservationLink = @ReservationLink,
		StartDate = @StartDate
	where IdMovie = @IdMovie
end
go


create proc deleteMovie
	@IdMovie int
as 
begin
	delete from MovieActor where MovieId = @IdMovie
	delete from MovieDirector where MovieId = @IdMovie
	delete from Movie where IdMovie = @IdMovie
end
go

create proc insertActorInMovie
	@IdMovie int,
	@IdActor int
as
begin
	insert into MovieActor (MovieId, ActorId) values (@IdMovie, @IdActor)
end
go

create proc insertDirectorInMovie
	@IdMovie int,
	@IdDirector int
as
begin
	insert into MovieDirector(MovieId, DirectorId) values (@IdMovie, @IdDirector)
end
go

create proc deleteActorFromMovie
	@IdMovie int,
    @IdActor int
as
begin
	delete from MovieActor where MovieId = @IdMovie and ActorId = @IdActor
end
go

create proc deleteActorsFromMovie
	@IdMovie int
as
begin
	delete from MovieActor where MovieId = @IdMovie
end
go

create proc deleteDirectorFromMovie
	@IdMovie int,
	@IdDirector int
as
begin
	delete from MovieDirector where MovieId = @IdMovie and DirectorId = @IdDirector
end
go

create proc deleteDirectorsFromMovie
	@IdMovie int
as
begin
	delete from MovieDirector where MovieId = @IdMovie
end
go




--USER


create table Role(
	IDRole int primary key identity,
	Name nvarchar(50)
)
go

create table AppUser(
	IDUser int primary key identity,
	Username nvarchar(50) not null,
	Password nvarchar(50) not null,
	RoleID int foreign key references Role(IDRole) not null,
	Active bit not null
)
go

insert into Role values ('Administrator'), ('Local user')
insert into AppUser values ('Admin', 'Admin', 1, 1)
go


--CLIENT CRUD

create proc createUser
	@Username nvarchar(50),
	@Password nvarchar(50),
	@Role nvarchar(50),
	@Active bit,
	@IDUser int output
as
begin
	insert into AppUser (Username, Password, RoleID, Active) values (@Username, @Password, (select IDRole from Role where Name = @Role), @Active)
	set @IDUser = SCOPE_IDENTITY()
end
go

create proc selectUsers 
as
begin 
	select u.IDUser, u.Username, u.Password, r.Name as Role, u.Active
	from AppUser as u
	inner join Role as r on u.RoleID = r.IDRole
end
go

create proc selectUser
	@IDUser int
as
begin 
	select u.IDUser, u.Username, u.Password, r.Name as Role, u.Active
	from AppUser as u
	inner join Role as r on u.RoleID = r.IDRole
	where IDUser = @IDUser
end
go

create proc updateUser
	@Username nvarchar(50),
	@Password nvarchar(50),
	@Role nvarchar(50),
	@Active bit,
	@IDUser int output
as
begin
	update AppUser set
		Username = @Username,
		Password = @Password,
		RoleID = (select IDRole from Role where Name = @Role),
		Active = @Active
	where IDUser = @IDUser
end
go

create proc deleteUser
	@IDUser int
as
begin
	delete from AppUser where IDUser = @IDUser
end
go

create proc deleteData
as
delete from MovieActor
delete from MovieDirector
delete from Actor
delete from Director
delete from Movie
