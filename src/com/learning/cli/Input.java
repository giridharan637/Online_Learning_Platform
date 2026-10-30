package com.learning.cli;

import java.util.Scanner;

/** All CLI menus must share one scanner for System.in. */
public final class Input {
    public static final Scanner SCANNER = new Scanner(System.in);
    private Input() { }
}
