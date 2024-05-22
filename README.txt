Running Instructions 
Please navigate to the src file of the solution and type in the following command to compile:
javac main.java  
After compilation run the code with the following command:
java P3main <DFS|BFS|AStar|BestF|SMAStar|...> <N> <d_s:angle_s> <d_g:angle_g>

Description of The World
Polar coordinates are used to identify points in the grid, represented as a two-dimensional
coordinate system in which each point on the 2d plane is identified by its distance from a
centre/pole. A polar coordinate is a tuple (d : angle), where d is the distance from the pole
within [0, N −1] and angle is the angle of direction, one of {0, 45, 90, 135, 180, 225, 270, 315}

Coordinates at the pole, such as (0:0) are valid coordinates. Aircrafts, however, cannot
reach or fly over the pole.

Aircrafts start at the S = (ds : angles) point and terminate at the G = (dg : angleg) point
(e.g. S=(2:45) and G=(1:180))

Aircrafts can only move in one of the 4 directions along the grid, where going North means
going to the centre (the Magnetic North), and we continue in a clockwise direction for East,
South, and West as shown in Figure 2.

Search Cost Description
The distance between two parallels is 1, and the distance between two meridians is 1/8
of the length of the circle at this parallel