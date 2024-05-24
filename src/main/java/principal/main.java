
package principal;

import test.frmplanilla;
public class main {

    public static void main(String[] args) {
        frmplanilla p = new frmplanilla();
        p.setVisible(true);
        p.setDefaultCloseOperation(frmplanilla.EXIT_ON_CLOSE);

        p.setExtendedState(frmplanilla.MAXIMIZED_BOTH);

    }

}
