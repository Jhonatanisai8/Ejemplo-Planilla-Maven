/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal;

import test.frmplanilla;

/**
 *
 * @author User
 */
public class main {

    public static void main(String[] args) {
        frmplanilla p = new frmplanilla();
        p.setVisible(true);
        p.setDefaultCloseOperation(frmplanilla.EXIT_ON_CLOSE);

        p.setExtendedState(frmplanilla.MAXIMIZED_BOTH);

    }

}
