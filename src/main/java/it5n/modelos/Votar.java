/*
 *  Eleciones IT5N por Hector Armando Herrera
 *
 * Este programa es software libre: usted puede distribuir y/o modificarlo
 * bajo los términos de la GNU General Public License publicada por
 * la Free Software Foundation, ya sea la versión 3 de la Licencia, o
 * versiones posteriores.
 *
 * Este programa se distribuye con la esperanza de que sea útil.,
 * pero SIN NINGUNA GARANTÍA; sin ni siquiera la garantía implícita de
 * COMERCIABILIDAD o APTITUD PARA UN PROPÓSITO PARTICULAR. Ver la
 * GNU General Public License para mas detalles.
 *
 * Debería haber recibido una copia de la GNU General Public License
 * junto con este programa. Si no, visite <http://www.gnu.org/licenses/>.
 */

package it5n.modelos;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

/**
 *
 * @author Héctor Armando Herrera (profeharmando@gmail.com)
 */
public class Votar {
    
    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private ImageView imageView4;
    private ImageView imageView5;
    private ImageView imageView6;
    
    public Votar(Image img1) {
        this.imageView1 = new ImageView();
        imageView1.setImage(img1);
    }
    
    public Votar(Image img1, Image img2) {
        this.imageView1 = new ImageView();
        imageView1.setImage(img1);        
        this.imageView2 = new ImageView();
        imageView2.setImage(img2);
    }
    
    public Votar(Image img1, Image img2, Image img3) {
        this.imageView1 = new ImageView();
        imageView1.setImage(img1);
        this.imageView2 = new ImageView();
        imageView2.setImage(img2);
        this.imageView3 = new ImageView();
        imageView3.setImage(img3);
    }
    
    public Votar(Image img1, Image img2, Image img3, Image img4) {
        this.imageView1 = new ImageView();
        imageView1.setImage(img1);
        this.imageView2 = new ImageView();
        imageView2.setImage(img2);
        this.imageView3 = new ImageView();
        imageView3.setImage(img3);
        this.imageView4 = new ImageView();
        imageView4.setImage(img4);
    }
 
    public Votar(Image img1, Image img2, Image img3, Image img4, Image img5) {
        this.imageView1 = new ImageView();
        imageView1.setImage(img1);
        this.imageView2 = new ImageView();
        imageView2.setImage(img2);
        this.imageView3 = new ImageView();
        imageView3.setImage(img3);
        this.imageView4 = new ImageView();
        imageView4.setImage(img4);
        this.imageView5 = new ImageView();
        imageView5.setImage(img5);
    }
    
    public Votar(Image img1, Image img2, Image img3, Image img4, Image img5, Image img6) {
        this.imageView1 = new ImageView();
        imageView1.setImage(img1);
        this.imageView2 = new ImageView();
        imageView2.setImage(img2);
        this.imageView3 = new ImageView();
        imageView3.setImage(img3);
        this.imageView4 = new ImageView();
        imageView4.setImage(img4);
        this.imageView5 = new ImageView();
        imageView5.setImage(img5);
        this.imageView6 = new ImageView();
        imageView6.setImage(img6);
    }
 
    public ImageView getImagen1() {
        return imageView1;
    }
    public void setImagen1(ImageView imagen) {
        this.imageView1 = imagen;
    }
    
    public ImageView getImagen2() {
        return imageView2;
    }
    public void setImagen2(ImageView imagen) {
        this.imageView2 = imagen;
    }
    
    public ImageView getImagen3() {
        return imageView3;
    }
    public void setImagen3(ImageView imagen) {
        this.imageView3 = imagen;
    }
    
    public ImageView getImagen4() {
        return imageView4;
    }
    public void setImagen4(ImageView imagen) {
        this.imageView4 = imagen;
    }
    
    public ImageView getImagen5() {
        return imageView5;
    }
    public void setImagen5(ImageView imagen) {
        this.imageView5 = imagen;
    }
    
    public ImageView getImagen6() {
        return imageView6;
    }
    public void setImagen6(ImageView imagen) {
        this.imageView6 = imagen;
    }
    
}
