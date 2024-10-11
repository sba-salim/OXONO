# Théorie

## Rappels OO

#### Question 1  

***

1. Qu’affiche ce programme (en réfléchissant, sur papier, sans l,  exécuter) ?  
(0.0, 0.0)  
(2.2, 2.2)

2. Si vous changez la visibilité de la classe TestPoint en public,  quelle erreur le compilateur rapporte-t-il ?  
La classe TestPoint est pubilque, elle doit donc être déclarée dans un  
fichier nommé TestPoint.java

3. Si vous changez la visibilité de la classe Point en visibilité par  
défaut (retirer le modificateur public), obtenez-vous une erreur ? Si  
oui laquelle ?  
Je n'ai pas obtenu d'erreur en faisant cette modification  

4. Remettez le code dans son état d’origine (la classe Point en public
et la classe TestPoint en visibilité par défaut).  

#### Question 2  

***

1. Ajoutez dans la méthode principale de la classe TestPoint la ligne :  

```
System.out.println(p.x);  
```

Quelle erreur obtient-on ? Remettez ensuite le code dans son état d’origine.  

x est une attribut privé, on ne peut donc pas l'appeler dans une autre  classe que Point.

2. Ajoutez la méthode suivante à la classe Point  

```java
public void move(int dx, int dy) {  
System.out.println("méthode move(int, int)");  
x += dx;  
y += dy;  
}  
```

Qu’affiche maintenant le programme ?  

```output
(0.0, 0.0)  
méthode move(int, int)  
(2.0, 2.0)  
```

On obtient ces affichages parce que les nombres entrés dans la méthode  move sont considérés comme des entiers, par contre si on écrivait :  
move(2.0, 2.0)  
je pense que la méthode appelée serai move(double, double)

3. Que se passe-t-il si on tente d’ajouter la méthode (le type de retour est différent
mais le nombre et le type des paramètres sont identiques)

public boolean move(double dx, double dy) {  
x += dx;  
y += dy;  
return true;  
}  

Une erreur de compilation est lancée car on ne peut pas avoir deux  méthodes ayant le même nom et les mêmes paramètres.  

4. Remettez le code dans son état d’origine

#### Question 3  

***

1. Modifiez le constructeur sans paramètre comme suit (ajout de l’appel println) :  

```java
public Point() {  
System.out.println("test");  
this(0,0);  
}  
```

Quelle erreur obtient-on ?  

Il y' a une erreur de compilation car dans un constructeur, this doit  être le première expression si elle est appelée.  

2. Supprimez le constructeur sans paramètre de la classe Point.
Quelle erreur de compilation obtient-on ?  

Il y'a une erreur car le constrcteur ne reçoit pas les paramètres qu'il attend  

3. Supprimez l’autre constructeur. La classe Point ne définit plus aucun constructeur.  
Obtient-on toujours une erreur ? Pourquoi ?  
Qu’affiche ce programme ?  

Lorsqu’une classe ne définit aucun constructeur, un constructeur par défaut sans
paramètre est créé, c’est ce constructeur qui est utilisé à la première ligne :
Point p = new Point();  

4. Remettez le code dans son état d’origine.

#### Question 4  

***

1. Qu’affiche ce programme ?  

Circle : [(0.0, 0.0), 5.0]  
Circle : [(2.0, 5.0), 5.0]  
Circle : [(2.0, 5.0), 10.0]  

2. Combien d’instances différentes de la classe Point sont créées dans ce programme ?  

Une seule instance de point est créé dans ce programme  

#### Question 5  

***

1. Qu’affiche ce programme ?  

Circle : [(0.0, 0.0), 5.0]  
Circle : [(2.0, 5.0), 5.0]  
Circle : [(0.0, 0.0), 5.0]  

On constate que le cercle est déplacé à chaque fois que le point est déplacé.  

2. Combien d’instances de la classe Point et Circle sont créées dans ce programme ?  
Quelles sont les instances référencées par la variable p et p2 dans le main ?  
Quelle instance référence l’attribut center de l’instance c créée dans le main ?  

p et p2 référencent la même instance de point, il n'y donc qu'une seule  instance de point créée, 'c' est aussi la seule instance de point créée.  

3. Ajoutez une copie défensive à la ligne 14 de la classe Circle comme suit :  

this.center = new Point(center.getX(), center.getY()); //copie défensive.  

Qu’affiche maintenant le programme TestDefensiveCopy ?  

Circle : [(0.0, 0.0), 5.0]  
Circle : [(0.0, 0.0), 5.0]  
Circle : [(-2.0, -5.0), 5.0]  
Puisqu'une copie défensive de center est faite dans le constructeur,  
le cercle c n'est plus déplacé quand on déplace p mais ça ne vaut pas  pour p2, car aucune copie défensive n'est faite dans getCenter.  
4. Remplacez la ligne 26 de la classe Circle par la ligne suivante :

return new Point(center.getX(), center.getY()); //copie défensive

Qu’affiche maintenant le programme TestDefensiveCopy ?

Circle : [(0.0, 0.0), 5.0]  
Circle : [(0.0, 0.0), 5.0]  
Circle : [(0.0, 0.0), 5.0]  
Le cercle n'est plus déplacé quand on déplaces les points car les  copies défensives sont bien faites dans la classe Circle.  

5. Après avoir effectué ces modifications au programme :  
Combien d’instances de la classe Point et Circle sont créées dans ce programme ?  
Quelles sont les instances référencées par la variable p et p2 dans le main ?  
Quelle instance référence l’attribut center de l’instance c créée dans le main ?  

Il y'a trois instance de point créées dans ce programme :  

- Une pour p  
- Une pour p2  
- Une pour center  

#### Question 6  

***

1. Qu’affiche le programme TestPoints ?  

(3.0, 6.0) - FF0000FF  
x: 3.0  
color : FF0000FF  

2. Quelle(s) erreur(s) de compilation obtient-on si on modifie la  première ligne comme suit :  

Point p = new ColoredPoint(2, 4, 0xFF0000FF);  

Quelle ligne pose problème et pourquoi ?  
A-t-on toujours une erreur si on supprime (ou commente) cette ligne  problématique ?  

C'est la dernière ligne qui pose problème car la classe point  n'implémente pas de méthode getColor. Mais l'appel du constructeur  fonctionne car une classe mére peut appeler le constructeur de sa  classe fille à condition qu'elle ne soit pas abstraite.  

3. Peut-on ajouter la ligne suivante au main ?  

ColoredPoint p2 = new Point(2, 4);  

Pourquoi ?  

Non car une classe fille ne peut pas appeler le constructeur de sa mère  
si c'était possible la classe fille serait incomplète.

4. Peut-on remplacer le contenu de la méthode toString() de  ColoredPoint via :

return this.x +" - "+ this.y +" - "+ this.color;  

Non car les attributs x et y sont définis dans la classe mère.  

5. Quelle erreur obtient-on si l’on modifie la déclaration de la classe  Point comme suit, expliquez :  

public class Point extends ColoredPoint {  
...  
}  

Il y'a un héritage cyclique entre les classes Point et ColoredPoint

6. Quelle erreur obtient-on si on déclare final la classe Point :  

public final class Point {  
...  
}  

Une classe ne peut pas hériter de d'une classe constante.  

7. Remettez le code dans son état d’origine.  

#### Question 7  

***

1. Peut-on ajouter la ligne suivante au main ?  

Object p3 = new Point(2, 4);  

Pourquoi ?  

Oui car en java toutes les classes héritent de Object

2. Peut-on ajouter la ligne suivante au main ?  

Object p4 = new ColoredPoint(2, 4, 0xFF0000FF);  

Pourquoi ?  
Oui car en java toutes les classes héritent de Object

3. Peut-on ajouter la ligne suivante au main ?  

p.hashCode();  

Où est définie cette méthode ?

elle est définie dans la classe Object

Pourquoi peut-on l’appeler sur un objet de type ColoredPoint ?
parce que coloredpoint hérite d'object.

4. Remettez le code dans son état d’origine.


#### Question 8

1. Quelle erreur de compilation obtenez-vous si vous ajoutez

```
System.out.println("test");
```
Super doit être la première expression du constructeur s'il est appelé.

comme première ligne du constructeur de la classe ColoredPoint ?
(retirez cette ligne après avoir fait le test)
2. Quelle erreur de compilation obtenez-vous si vous supprimez la ligne

```
super(x,y);
```

dans le constructeur de la classe ColoredPoint ? A quoi sert cette ligne ?

Il n'ya pas de constructeur sans paramètre dans la classe point.

3. Après avoir supprimé la ligne au point précédent, ajoutez dans la classe Point le
constructeur suivant :

```
public Point() {
this(0,0);
}
```

A-t-on toujours la même erreur qu’au point précédent ?

Non car le constructeur sans paramètre de Point à été défini

4. Remettez le code dans son état d’origine.

#### Question 9

1. Qu’affiche ce programme ?

```
constructor of A
constructor of B
constructor of C
```

2. Supprimez le constructeur de la classe C, qu’affiche maintenant le programme ?

```
constructor of A
constructor of B
```

3. Après avoir remis le constructeur de la classe C, ajoutez dans chaque constructeur
un appel explicite au constructeur de la super-classe (super();). Vérifiez que l’effet
est bien identique.

l'effet est identique.

4. À votre avis, quelles sont les constructeurs de la classe Object ? Vérifiez en consultant la javadoc.

Objet() {};

5. Remettez le code dans son état d’origine.

#### Question 10

1. Qu’affiche ce programme ?

(0.0, 0.0) - not pinned
(1.0, 1.0) - pinned

2. Selon vous, quelle méthode move est exécutée :
▷ celle de Point car la variable est déclarée de type Point ?
▷ celle de PinnablePoint car l’objet référencé par la variable à ce moment-là
est de type PinnablePoint ?  

celle de Point car la variable est déclarée de type Point ?

3. Ajoutez une exception à la méthode move.

```
@Override
public Point move(double dx, double dy) throws Exception {
if(!pinned)
super.move(dx, dy);
else throw new Exception("Point is pinned, cannot move anymore");
return this;
}
```

Quelle erreur obtenez-vous ?

Il y'a un clash entre les méthodes car elles n'ont pas la même signature

4. Remplacez Exception par une IllegalStateException. Avez-vous toujours une
erreur ? Pourquoi ?

Non car toutes les méthodes lancent cette exception, et cela est déclaré implicitement

5. Retirez la clause throws (mais gardez le throw), avez–vous une erreur ? 

Non et cela est dû auchangement dans la signature de la méthode.

6. Remplacez le type de retour par PinnablePoint, avez-vous une erreur ?

Bizzarement, malgré le changement de type de retour aucune erreur n'est lancée à la compilation, cela est probablement dû à l'héritage.

7. Remplacez le type de retour par Object, obtenez-vous une erreur ? pourquoi ?

Parce que Object n'est pas considéré comme un point alors que PinnablePoint l'est.

8. Remplacez le modificateur d’accès public par protected, quelle erreur obtienton ?

Parce que public et protected ne peuvent pas se combiner.

9. Dans la méthode move, que fait l’appel "super.move(...);" ?

il permet d'appeler la méthode move de point pour modifier les attributs hérités.


#### Question 11

1. Ajoutez le mot clé protected devant la méthode move. Que constatez-vous ?

il y'a une erreur, il faut changer la signature de toutes les implémentations de move(protected).

2. Remplacez protected par public. Que constatez-vous ?

la méthode est publique par défaut, aucun changement à noter.

3. Modifiez la déclaration de la classe Point afin qu’elle implémente l’interface
Movable :

```
public class Point implements Movable {
...
}
```

4. Remplacez Point par Movable dans les déclarations de TestPolymorphisme.
Qu’affiche le programme ?

J'ai une erreur peut être que je n'ai pas bien remis le code à son état d'origine.

## Regex

#### Question 1

1. matricule de l'école "g/d{5}"
2. "(g\d{5}\s){5}"
3. "Bonjour.*merci."
4. "add\scircle\s((\d)+\s){3}\w"
5. /"move\s(\d)+\s((-)?(\d)+\s?){2}"

#### Question 2

Le group zero contient la date en complète.

## Generics

#### Question 1

1. 
'setElement(java.lang.Integer)' in 'esi.generics.Box' cannot be applied to '(java.lang.Double)'
ILe compilateur considére que la méthode setElement ne peut recervoir que des Integer (dans ce cas là), car la Box a été initialisée comme une box d'Integer

2. Le compilateur précise qu'il n'est pas nécessaire de déclarer le type que la box va prendre explicitement, cela est dû au fait qu'on ajoute directement un élément dans la box, le compilateur peut donc interpréter directement le type de l'élément. 
**Remarque** : Depuis java 9, Le constructeur Integer(int) ne peut plus être utilisé en java

#### Question 2

Non, cela est du au fait que Box<Integer> n'hérite pas de Box<Object>

#### Question 3

1. 
java: incompatible types: esi.generics.Box<java.lang.Integer> cannot be converted to esi.generics.Box<java.lang.Object>
(cf question 2).

2. 'setElement(java.lang.Integer)' in 'esi.generics.Box' cannot be applied to '(double)'
   Car on ne peut pas rajouter un double dans une box d'integer.

#### Question 4

1. 'setElement(capture<?>)' in 'esi.generics.Box' cannot be applied to '(java.lang.Integer)'
   il refuse de prendre un integer à la ligne 12 car la box à été initialisée avec un joker,
   Il faut donc que le types de l'argument passsé à setElement soit compatible avec tous les types.

#### Question 5

1. Les appels de compareTo ne sont pas reconnus car c'est une méthode de la superc lasse compare.

#### Question 6

Type parameter 'java.lang.Number' is not within its bound; should implement 'java.lang.Comparable<java.lang.Number>'
Incompatible types. Found: 'esi.generics.Pair<java.lang.Integer>', required: 'esi.generics.Pair<java.lang.Number>'
On attend exactement un Number et pas autre chose.

#### Question 7

1. 
copy(List<Shape> d, List<Circle> s)
copy2(List<Shape> d, List<Circle> s)//renvoie une erreur

2. 
