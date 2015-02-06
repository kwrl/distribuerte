import java.rmi.registry.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class RunTic {
	private static Registry reg;
	private static boolean isPlayerOne;

	public static void init_registry() {
		try {
			reg = LocateRegistry.createRegistry(Settings.REGISTRY_PORT);
			isPlayerOne = true;
			System.out.println("Registry setup");
		} catch (RemoteException err) {
			try {
				reg = LocateRegistry.getRegistry(Settings.REGISTRY_PORT);
				isPlayerOne = false;
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			System.out.println("Connected to registry");
		}
	}

	public static void main(String[] args) {
		init_registry();
		try {
			new RemoteTicTacToeImpl(reg, isPlayerOne);
		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
	}
}
